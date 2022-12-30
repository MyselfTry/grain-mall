package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.SkuLadderDao;
import com.java.gmall.sms.entity.SkuLadder;
import com.java.gmall.sms.service.SkuLadderService;

@Service("skuLadderService")
public class SkuLadderServiceImpl extends ServiceImpl<SkuLadderDao, SkuLadder> implements SkuLadderService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuLadder> page = this.page(
                new Query<SkuLadder>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}