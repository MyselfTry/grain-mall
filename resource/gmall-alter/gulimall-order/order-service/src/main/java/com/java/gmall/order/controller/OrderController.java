package com.java.gmall.order.controller;

import com.java.core.bean.Resp;
import com.java.core.utils.IdWorker;
import com.java.gmall.order.interceptor.LoginInterceptor;
import com.java.gmall.order.service.OrderService;
import com.java.gmall.order.vo.OrderConfirmVO;
import com.java.gmall.oms.vo.OrderSubmitVO;
import com.java.gmall.wms.vo.SkuLockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiangli
 * @since 2020/1/30 11:19
 */
@RestController
@RequestMapping("order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping("confirm")
	public Resp<OrderConfirmVO> confirm() {
		OrderConfirmVO orderConfirmVO = orderService.confirm();
		return Resp.ok(orderConfirmVO);
	}

	@PostMapping("submit")
	public Resp<Object> submit(@RequestBody OrderSubmitVO submitVO) {
		orderService.submit(submitVO);

		return Resp.ok(null);
	}

	/**
	 * 秒杀伪代码
	 * 分布式并发工具类，快速的腾出服务器的资源来处理其他请求；
	 */
/*	@GetMapping("/miaosha/{skuId}")
	public Resp<Object> kill(@PathVariable("skuId") Long skuId){
		Long userId = LoginInterceptor.getUserInfo().getId();
		if(userId!=null){
			// 查询库存
			String stock = this.redisTemplate.opsForValue().get("sec:stock:" + skuId);
			if (StringUtils.isEmpty(stock)){
				return Resp.fail("秒杀结束！");
			}

			// 通过信号量，获取秒杀库存
			RSemaphore semaphore = this.redissonClient.getSemaphore("sec:semaphore:" + skuId);
			semaphore.trySetPermits(Integer.valueOf(stock));
			//0.1s
			boolean b = semaphore.tryAcquire();
			if(b){
				//创建订单
				String orderSn = IdWorker.getTimeId();

				SkuLockVO lockVO = new SkuLockVO();
				lockVO.setOrderToken(orderSn);
				lockVO.setNum(1);
				lockVO.setSkuId(skuId);

				//准备闭锁信息
				RCountDownLatch latch = this.redissonClient.getCountDownLatch("sec:countdown:" + orderSn);
				latch.trySetCount(1);

				this.amqpTemplate.convertAndSend("SECKILL-ORDER", "sec.kill", lockVO);
				return Resp.ok("秒杀成功，订单号：" + orderSn);
			}else {
				return Resp.fail("秒杀失败，欢迎再次秒杀！");
			}
		}
		return Resp.fail("请登录后再试！");
	}

	@GetMapping("/miaosha/pay")
	public String payKillOrder(String orderSn) throws InterruptedException {

		RCountDownLatch latch = this.redissonClient.getCountDownLatch("sec:countdown:" + orderSn);

		latch.await();

		// 查询订单信息

		return "";
	}*/
}
