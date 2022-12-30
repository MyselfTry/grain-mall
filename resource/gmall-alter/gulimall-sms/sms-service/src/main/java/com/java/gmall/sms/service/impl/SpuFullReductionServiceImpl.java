package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.SpuFullReductionDao;
import com.java.gmall.sms.entity.SpuFullReduction;
import com.java.gmall.sms.service.SpuFullReductionService;

@Service("spuFullReductionService")
public class SpuFullReductionServiceImpl extends ServiceImpl<SpuFullReductionDao, SpuFullReduction> implements SpuFullReductionService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuFullReduction> page = this.page(
                new Query<SpuFullReduction>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}