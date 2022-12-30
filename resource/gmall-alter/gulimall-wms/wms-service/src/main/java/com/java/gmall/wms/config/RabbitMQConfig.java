package com.java.gmall.wms.config;

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
 */
@Configuration
public class RabbitMQConfig {

	/**
	 * 定义延时队列
	 */
	@Bean("WMS-TTL-QUEUE")
	public Queue ttlQueue(){
		//延时队列中的消息过期了，会自动触发消息的转发，通过指定routing-key发送到指定exchange中
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "gmall-cart-exchange");
		arguments.put("x-dead-letter-routing-key", "cart.unlock");
		arguments.put("x-message-ttl", 120000); // 单位:毫秒 2分钟仅仅用于测试，实际根据需求，通常30分钟或者15分钟
		return new Queue("WMS-TTL-QUEUE", true, false, false, arguments);
	}


	/**
	 * 延时队列绑定到交换机
	 * rountingKey：store.create
	 */
	@Bean("WMS-TTL-BINDING")
	public Binding ttlBinding(){
		// 锁定库存后会发送一条routingKey=store.create的消息到GMALL-ORDER-EXCHANGE,然后会被路由到延时队列WMS-TTL-QUEUE,延时队列没有消费者,到期后会将消息转发
		return new Binding("WMS-TTL-QUEUE", Binding.DestinationType.QUEUE, "GMALL-ORDER-EXCHANGE", "store.create", null);
	}

}
