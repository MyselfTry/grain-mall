package com.java.gmall.oms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.oms.dao.RefundInfoDao;
import com.java.gmall.oms.entity.RefundInfo;
import com.java.gmall.oms.service.RefundInfoService;

@Service("refundInfoService")
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoDao, RefundInfo> implements RefundInfoService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<RefundInfo> page = this.page(
                new Query<RefundInfo>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}