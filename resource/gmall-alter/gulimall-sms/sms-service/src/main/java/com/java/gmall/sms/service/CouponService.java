package com.java.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.sms.entity.Coupon;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 优惠券信息
 *
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
public interface CouponService extends IService<Coupon> {

    PageVo queryPage(QueryCondition params);
}

