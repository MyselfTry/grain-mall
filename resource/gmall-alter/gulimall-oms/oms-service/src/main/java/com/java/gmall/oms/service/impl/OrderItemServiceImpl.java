package com.java.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.oms.dao.OrderItemDao;
import com.java.gmall.oms.entity.OrderItem;
import com.java.gmall.oms.service.OrderItemService;

@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItem> implements OrderItemService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<OrderItem> page = this.page(
                new Query<OrderItem>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}