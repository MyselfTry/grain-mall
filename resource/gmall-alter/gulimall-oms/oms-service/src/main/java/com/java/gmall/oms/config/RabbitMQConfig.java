package com.java.gmall.oms.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangli
 * @since 2020/1/31 20:30
 * 1. 订单创建成功，发送消息到创建订单的路由
   2. 创建订单的路由转发消息给延时队列，延时队列的延时时间就是订单从创建到支付过程，允许的最大等待时间。延时队列不能有消费者（即消息不能被消费）
   3. 延时时间一到，消息被转入DLX（死信路由）
   4. 死信路由把死信消息转发给死信队列
   5. 订单系统监听死信队列，获取到死信消息后，执行关单解库存操作
 */
@Configuration
public class RabbitMQConfig {

	/**
	 * 定义交换机
	 */
	@Bean
	public Exchange exchange(){
		return new TopicExchange("GMALL-ORDER-EXCHANGE", true, false, null);
	}

	/**
	 * 定义延时队列
	 */
	@Bean("ORDER-TTL-QUEUE")
	public Queue ttlQueue(){
		//延时队列中的消息过期了，会自动触发消息的转发，通过指定routing-key发送到指定exchange中
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "GMALL-ORDER-EXCHANGE");
		arguments.put("x-dead-letter-routing-key", "order.close");
		arguments.put("x-message-ttl", 120000); // 单位:毫秒 2分钟仅仅用于测试，实际根据需求，通常30分钟或者15分钟
		return new Queue("ORDER-TTL-QUEUE", true, false, false, arguments);
	}


	/**
	 * 延时队列绑定到交换机
	 * rountingKey：order.create
	 */
	@Bean("ORDER-TTL-BINDING")
	public Binding ttlBinding(){
		// 订单入库成功后会发送一条routingKey=order.create的消息到GMALL-ORDER-EXCHANGE,然后会被路由到延时队列ORDER-TTL-QUEUE,延时队列没有消费者,到期后会将消息转发
		return new Binding("ORDER-TTL-QUEUE", Binding.DestinationType.QUEUE, "GMALL-ORDER-EXCHANGE", "order.create", null);
	}

	/**
	 * 死信队列 (普通队列)
	 */
	@Bean("ORDER-CLOSE-QUEUE")
	public Queue queue(){

		return new Queue("ORDER-CLOSE-QUEUE", true, false, false, null);
	}

	/**
	 * 死信队列绑定到交换机
	 * routingKey：order.close 和延时队列的routingKey一致,延时队列将消息转发给exchange,exchange再路由到死信队列
	 */
	@Bean("ORDER-CLOSE-BINDING")
	public Binding closeBinding(){

		return new Binding("ORDER-CLOSE-QUEUE", Binding.DestinationType.QUEUE, "GMALL-ORDER-EXCHANGE", "order.close", null);
	}

}
