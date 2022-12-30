package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.CouponDao;
import com.java.gmall.sms.entity.Coupon;
import com.java.gmall.sms.service.CouponService;

@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponDao, Coupon> implements CouponService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<Coupon> page = this.page(
                new Query<Coupon>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}