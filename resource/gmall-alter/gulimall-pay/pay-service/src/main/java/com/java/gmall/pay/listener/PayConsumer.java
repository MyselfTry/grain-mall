package com.java.gmall.pay.listener;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.java.gmall.pay.config.AlipayTemplate;
import com.java.gmall.pay.vo.PayVo;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jiangli
 * @since 2020/2/1 13:19
 */
@Component
public class PayConsumer {
	@Autowired
	private AlipayTemplate alipayTemplate;

	/**
	 * 支付
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "gmall.order.pay.queue", durable = "true"),
			exchange = @Exchange(
					value = "GMALL-ORDER-EXCHANGE",
					ignoreDeclarationExceptions = "true",
					type = ExchangeTypes.TOPIC),
			key = {"order.pay"}
	))
	public void pay(PayVo payVo) {
		try {
			String form = alipayTemplate.pay(payVo);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

	}
}
