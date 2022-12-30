package com.java.gmall.oms.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.gmall.oms.dao.OrderDao;
import com.java.gmall.oms.entity.Order;
import com.java.gmall.ums.vo.UserBoundVO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author jiangli
 * @since 2020/1/31 20:42
 */
@Component
@Slf4j
public class OmsListener {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * 监听死信队列
	 */
	@RabbitListener(queues = {"ORDER-CLOSE-QUEUE"})
	public void closeOrder(String orderToken, Channel channel, Message message) {
		try {
			// 关单
			if (this.orderDao.closeOrder(orderToken) == 1) {
				// 如果关单成功，发送消息给库存系统，释放库存
				this.amqpTemplate.convertAndSend("gmall-cart-exchange", "cart.unlock", orderToken);
			}
			// 如果关单失败，说明订单可能已被关闭，直接确认消息
			// 手动ACK
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			// 消费失败后重新入队
			try {
				channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
			} catch (IOException e1) {
				log.error("释放库存失败,orderToken=[{}]", orderToken);
			}
		}
	}

	/**
	 * 支付成功后修改订单状态
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "gmall.pay.success.queue", durable = "true"),
			exchange = @Exchange(value = "GMALL-ORDER-EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
			key = {"pay.success"}
	))
	public void listenerCartDelete(String orderToken) {
		// 更新订单状态
		Integer count = orderDao.updateOrderStatus(orderToken);
		if (count == 1) {
			// 发送消息减库存
			 this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE", "stock.minus", orderToken);
			// 发送消息加积分
			Order order = orderDao.selectOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderSn, orderToken));
			UserBoundVO userBoundVO = new UserBoundVO();
			userBoundVO.setUserId(order.getMemberId());
			userBoundVO.setIntegration(order.getIntegration());
			userBoundVO.setGrowth(order.getGrowth());
			// TODO
//			this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE", "bound.plus", userBoundVO);
			// TODO 删除redis中锁定的库存
		}
	}
}
