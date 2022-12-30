package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.CouponHistoryDao;
import com.java.gmall.sms.entity.CouponHistory;
import com.java.gmall.sms.service.CouponHistoryService;

@Service("couponHistoryService")
public class CouponHistoryServiceImpl extends ServiceImpl<CouponHistoryDao, CouponHistory> implements CouponHistoryService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<CouponHistory> page = this.page(
                new Query<CouponHistory>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}