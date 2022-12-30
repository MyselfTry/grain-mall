package com.java.gmall.order.service;

import com.java.core.bean.Resp;
import com.java.core.bean.UserInfo;
import com.java.core.exception.RRException;
import com.java.core.utils.IdWorker;
import com.java.gmall.cart.pojo.Cart;
import com.java.gmall.oms.entity.Order;
import com.java.gmall.order.feign.*;
import com.java.gmall.order.interceptor.LoginInterceptor;
import com.java.gmall.order.vo.OrderConfirmVO;
import com.java.gmall.oms.vo.OrderItemVO;
import com.java.gmall.oms.vo.OrderSubmitVO;
import com.java.gmall.pay.vo.PayVo;
import com.java.gmall.pms.entity.SkuInfo;
import com.java.gmall.pms.entity.SkuSaleAttrValue;
import com.java.gmall.ums.entity.Member;
import com.java.gmall.ums.entity.MemberReceiveAddress;
import com.java.gmall.wms.vo.SkuLockVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author jiangli
 * @since 2020/1/30 11:22
 */
@Service
@Slf4j
public class OrderService {
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private ThreadPoolExecutor threadPoolExecutor;
	@Autowired
	private UmsFeign umsFeign;
	@Autowired
	private CartFeign cartFeign;
	@Autowired
	private PmsFeign pmsFeign;
	@Autowired
	private WmsFeign wmsFeign;
	@Autowired
	private OmsFeign omsFeign;
	@Autowired
	private AmqpTemplate amqpTemplate;

	private static final String ORDER_TOKEN = "order:token:";

	public OrderConfirmVO confirm() {
		UserInfo userInfo = LoginInterceptor.userInfo();
		Long userId = userInfo.getId();
		if (userId == null) {
			return null;
		}

		OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
		// 用户收货地址
		// runAsync 创建一个新的,没有返回值
		CompletableFuture<Void> addressesCompletableFuture = CompletableFuture.runAsync(() -> {
			List<MemberReceiveAddress> addresses = umsFeign.queryMemberReceiveAddressByUserId(userId);
			orderConfirmVO.setAddresses(addresses);
		}, threadPoolExecutor);

		// 获取购物车中选择的商品信息
		CompletableFuture<Void> cartsCompletableFuture = CompletableFuture.runAsync(() -> {
			List<Cart> carts = cartFeign.queryCheckedCartsByUserId(userId);
			if (CollectionUtils.isEmpty(carts)) {
				throw new RRException("至少选择一件商品支付");
			}
			List<OrderItemVO> orderItemVOS = carts.stream().map(cart -> {
				OrderItemVO orderItemVO = new OrderItemVO();
				orderItemVO.setSkuId(cart.getSkuId());
				SkuInfo skuInfo = pmsFeign.querySkuBySkuId(cart.getSkuId());
				orderItemVO.setWeight(skuInfo.getWeight());
				orderItemVO.setCount(cart.getCount());
				orderItemVO.setDefaultImage(skuInfo.getSkuDefaultImg());
				orderItemVO.setPrice(skuInfo.getPrice());
				orderItemVO.setTitle(skuInfo.getSkuTitle());
				List<SkuSaleAttrValue> skuSaleAttrValues = pmsFeign.querySkuSaleAttrValueBySkuId(cart.getSkuId());
				orderItemVO.setSkuAttrValue(skuSaleAttrValues);
				return orderItemVO;
			}).collect(Collectors.toList());
			orderConfirmVO.setOrderItems(orderItemVOS);
		}, threadPoolExecutor);

		// 用户积分
		CompletableFuture<Void> boundsCompletableFuture = CompletableFuture.runAsync(() -> {
			Resp<Member> memberResp = umsFeign.queryUserInfo(userId);
			orderConfirmVO.setBounds(memberResp.getData().getIntegration());
		}, threadPoolExecutor);

		// 生成唯一标识,防止重复提交
		CompletableFuture<Void> orderTokenCompletableFuture = CompletableFuture.runAsync(() -> {
			String orderToken = String.valueOf(new IdWorker(1, 1).nextId());
			orderConfirmVO.setOrderToken(orderToken);
			redisTemplate.opsForValue().set(ORDER_TOKEN + orderToken, orderToken);
		}, threadPoolExecutor);


		CompletableFuture.allOf(addressesCompletableFuture, cartsCompletableFuture, boundsCompletableFuture, orderTokenCompletableFuture).join();

		return orderConfirmVO;
	}

	public void submit(OrderSubmitVO submitVO) {
		String orderToken = submitVO.getOrderToken();
		// 1.防止重复提交验证.查询redis中有没有orderToken信息,有,则是第一次提交,放行并删除redis中的orderToken信息
		/*使用LUA脚本保证查询和删除的原子性,删除成功返回1,失败返回0*/
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Long flag = this.redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(ORDER_TOKEN + orderToken), orderToken);
		if (flag == 0L) {
			throw new RRException("订单不可重复提交");
		}

		// 2.校验价格,总价一致放行
		List<OrderItemVO> orderItems = submitVO.getOrderItems();
		if (CollectionUtils.isEmpty(orderItems)) {
			throw new RRException("请选择商品再提交");
		}
		BigDecimal currentTotalPrice = orderItems.stream().map(item -> {
			// 查询数据库中商品的价格
			SkuInfo skuInfo = pmsFeign.querySkuBySkuId(item.getSkuId());
			if (skuInfo == null) {
				return new BigDecimal("0");
			}
			return skuInfo.getPrice().multiply(new BigDecimal(item.getCount()));
		}).reduce(new BigDecimal("0"), BigDecimal::add);
		if (currentTotalPrice.compareTo(submitVO.getTotalPrice()) != 0) {
			throw new RRException("页面已过期,请刷新后重试");
		}

		// 3.校验库存是否足够并锁定库存
		List<SkuLockVO> skuLockVOS = orderItems.stream().map(orderItemVO -> {
			SkuLockVO skuLockVO = new SkuLockVO();
			skuLockVO.setSkuId(orderItemVO.getSkuId());
			skuLockVO.setCount(orderItemVO.getCount());
			skuLockVO.setOrderToken(orderToken);
			return skuLockVO;
		}).collect(Collectors.toList());
		Resp<Object> resp = wmsFeign.checkAndLockStore(skuLockVOS);
		if (resp.getCode() != 0) {
			throw new RRException(resp.getMsg());
		}

		// 4.下单
		UserInfo userInfo = LoginInterceptor.userInfo();
		submitVO.setUserId(userInfo.getId());
		Order order;
		try {
			order = omsFeign.saveOrder(submitVO);
		} catch (Exception e) {
			e.printStackTrace();
			// 发送消息给wms,解锁对应的库存 (补偿机制) 订单创建失败.马上释放库存
			try {
				this.amqpTemplate.convertAndSend("gmall-cart-exchange", "cart.unlock", orderToken);
			} catch (Exception ex) {
				log.error("解锁库存失败,orderToken=[{}]", orderToken);
			}

			throw new RRException("服务器错误,创建订单失败");
		}

		// 5.删除购物车(异步)
		Map<String, Object> msg = new HashMap<>();
		msg.put("userId", userInfo.getId());
		List<String> skuIds = orderItems.stream().map(orderItemVO -> orderItemVO.getSkuId().toString()).collect(Collectors.toList());
		msg.put("skuIds", skuIds);
		this.sendMessage("cart.delete", msg);

		// 6.支付
		PayVo payVo = new PayVo();
		payVo.setOut_trade_no(order.getOrderSn());
		payVo.setTotal_amount(order.getPayAmount().toString());
		payVo.setSubject("谷粒");
		payVo.setBody("谷粒商城订单");
		try {
			this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE", "order.pay", payVo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("订单[{}]支付异常", order.getOrderSn());
		}

	}

	/**
	 * 向mq发送消息
	 */
	private void sendMessage(String routingKey, Map<String, Object> msg) {
		// 发送消息
		try {
			// 这里没有指定交换机，因此默认发送到了配置中的：gmall.item.exchange
			this.amqpTemplate.convertAndSend("gmall-cart-exchange", routingKey, msg);
		} catch (Exception e) {
			log.error("[用户ID{}]删除购物车异常", msg.get("userId"));
		}
	}
}
