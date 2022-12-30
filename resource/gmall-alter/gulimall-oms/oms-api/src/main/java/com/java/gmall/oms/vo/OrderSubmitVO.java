package com.java.gmall.oms.vo;

import com.java.gmall.ums.entity.MemberReceiveAddress;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderSubmitVO {

    // 提交上次订单确认页给你的令牌；
    private String orderToken;
    // 收货地址
	private MemberReceiveAddress address;
	// 0-在线支付  1-货到付款
	private Integer payType;
	// 配送方式
	private String deliveryCompany;
	// 订单清单
	private List<OrderItemVO> orderItems;
	// 积分
	private Integer bounds;
	// 校验总价格时，拿计算价格和这个价格比较
    private BigDecimal totalPrice;
    // 用户id
	private Long userId;

}