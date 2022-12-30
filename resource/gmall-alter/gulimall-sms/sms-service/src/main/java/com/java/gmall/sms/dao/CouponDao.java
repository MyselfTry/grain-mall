package com.java.gmall.sms.dao;

import com.java.gmall.sms.entity.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Mapper
public interface CouponDao extends BaseMapper<Coupon> {
	
}
