package com.java.gmall.pay.controller;

import com.java.gmall.pay.vo.PayAsyncVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangli
 * @since 2020/2/1 12:28
 */
@RestController
@RequestMapping("pay")
public class PayController {
	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * 支付宝支付回调
	 */
	@PostMapping("alipay_callback")
	public void alipayCallback(PayAsyncVo payAsyncVo) {
		if ("TRADE_SUCCESS".equals(payAsyncVo.getTrade_status())) {
			// 发送消息,更新订单状态
			amqpTemplate.convertSendAndReceive("GMALL-ORDER-EXCHANGE", "pay.success", payAsyncVo.getOut_trade_no());
		}
	}


}
