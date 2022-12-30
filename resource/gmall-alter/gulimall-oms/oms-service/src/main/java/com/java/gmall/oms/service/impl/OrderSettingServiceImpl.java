package com.java.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.oms.dao.OrderSettingDao;
import com.java.gmall.oms.entity.OrderSetting;
import com.java.gmall.oms.service.OrderSettingService;

@Service("orderSettingService")
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingDao, OrderSetting> implements OrderSettingService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<OrderSetting> page = this.page(
                new Query<OrderSetting>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}