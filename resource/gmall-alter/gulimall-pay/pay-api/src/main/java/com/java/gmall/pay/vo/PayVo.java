package com.java.gmall.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayVo implements Serializable {
    private String out_trade_no; // 商户订单号 必填
    private String subject; // 订单名称 必填
    private String total_amount;  // 付款金额 必填
    private String body; // 商品描述 可空
}
