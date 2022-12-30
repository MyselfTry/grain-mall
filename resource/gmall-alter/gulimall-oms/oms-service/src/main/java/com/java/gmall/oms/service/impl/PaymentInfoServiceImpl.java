package com.java.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.oms.dao.PaymentInfoDao;
import com.java.gmall.oms.entity.PaymentInfo;
import com.java.gmall.oms.service.PaymentInfoService;

@Service("paymentInfoService")
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoDao, PaymentInfo> implements PaymentInfoService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<PaymentInfo> page = this.page(
                new Query<PaymentInfo>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}