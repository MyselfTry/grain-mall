package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.SeckillSessionDao;
import com.java.gmall.sms.entity.SeckillSession;
import com.java.gmall.sms.service.SeckillSessionService;

@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSession> implements SeckillSessionService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SeckillSession> page = this.page(
                new Query<SeckillSession>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}