package com.java.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.oms.dao.OrderReturnApplyDao;
import com.java.gmall.oms.entity.OrderReturnApply;
import com.java.gmall.oms.service.OrderReturnApplyService;

@Service("orderReturnApplyService")
public class OrderReturnApplyServiceImpl extends ServiceImpl<OrderReturnApplyDao, OrderReturnApply> implements OrderReturnApplyService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<OrderReturnApply> page = this.page(
                new Query<OrderReturnApply>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}