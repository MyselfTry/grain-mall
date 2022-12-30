package com.java.gmall.oms.service.impl;

import com.java.core.bean.Resp;
import com.java.gmall.oms.entity.OrderItem;
import com.java.gmall.oms.feign.UmsFeign;
import com.java.gmall.oms.service.OrderItemService;
import com.java.gmall.oms.vo.OrderItemVO;
import com.java.gmall.oms.vo.OrderSubmitVO;
import com.java.gmall.ums.entity.Member;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.oms.dao.OrderDao;
import com.java.gmall.oms.entity.Order;
import com.java.gmall.oms.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {
	@Autowired
	private UmsFeign umsFeign;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	public PageVo queryPage(QueryCondition params) {
		IPage<Order> page = this.page(
				new Query<Order>().getPage(params),
				new QueryWrapper<>()
		);

		return new PageVo(page);
	}

	@Override
	@Transactional
	public Order saveOrder(OrderSubmitVO orderSubmitVO) {
		// 保存order
		Order order = new Order();
		BeanUtils.copyProperties(orderSubmitVO.getAddress(), order);
		order.setOrderSn(orderSubmitVO.getOrderToken());
		order.setMemberId(orderSubmitVO.getUserId());
		order.setCreateTime(new Date());
		order.setTotalAmount(orderSubmitVO.getTotalPrice());
		order.setPayAmount(orderSubmitVO.getTotalPrice());
		order.setPayType(orderSubmitVO.getPayType());
		order.setStatus(0);
		order.setDeliveryCompany(orderSubmitVO.getDeliveryCompany());
		Resp<Member> memberResp = umsFeign.queryUserInfo(orderSubmitVO.getUserId());
		order.setMemberUsername(memberResp.getData().getUsername());
		order.setIntegration(100); //积分
		order.setGrowth(100);
		order.setDeleteStatus(0);
		order.setSourceType(1);
		order.setPayType(orderSubmitVO.getPayType());

		this.save(order);

		// 保存order_item
		List<OrderItemVO> orderItems = orderSubmitVO.getOrderItems();
		for (OrderItemVO orderItem : orderItems) {
			OrderItem itemEntity = new OrderItem();

			// 订单信息
			itemEntity.setOrderId(order.getId());
			itemEntity.setOrderSn(order.getOrderSn());

			// 需要远程查询spu信息 TODO

			// 设置sku信息
			itemEntity.setSkuId(orderItem.getSkuId());
			itemEntity.setSkuName(orderItem.getTitle());
			itemEntity.setSkuPrice(orderItem.getPrice());
			itemEntity.setSkuQuantity(orderItem.getCount());

			//需要远程查询优惠信息 TODO

			orderItemService.save(itemEntity);
		}

		// 订单创建之后发送消息到延时队列进行定时关单
		amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE", "order.create", orderSubmitVO.getOrderToken());

		return order;
	}

}