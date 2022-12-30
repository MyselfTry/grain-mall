package com.java.gmall.sms.vo;

import lombok.Data;

/**
 * 所有的优惠信息
 */
@Data
public class SaleVO {

	// 优惠类型
	private String type;

	//促销信息/优惠券的展示在前台的名称
	private String name;
}

