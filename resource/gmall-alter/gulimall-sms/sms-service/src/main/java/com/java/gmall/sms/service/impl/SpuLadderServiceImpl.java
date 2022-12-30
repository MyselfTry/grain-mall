package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.SpuLadderDao;
import com.java.gmall.sms.entity.SpuLadder;
import com.java.gmall.sms.service.SpuLadderService;

@Service("spuLadderService")
public class SpuLadderServiceImpl extends ServiceImpl<SpuLadderDao, SpuLadder> implements SpuLadderService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuLadder> page = this.page(
                new Query<SpuLadder>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}