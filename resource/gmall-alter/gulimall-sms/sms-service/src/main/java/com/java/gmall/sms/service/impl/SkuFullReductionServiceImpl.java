package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.SkuFullReductionDao;
import com.java.gmall.sms.entity.SkuFullReduction;
import com.java.gmall.sms.service.SkuFullReductionService;

@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReduction> implements SkuFullReductionService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuFullReduction> page = this.page(
                new Query<SkuFullReduction>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}