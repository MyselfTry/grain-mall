package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.SeckillPromotionDao;
import com.java.gmall.sms.entity.SeckillPromotion;
import com.java.gmall.sms.service.SeckillPromotionService;

@Service("seckillPromotionService")
public class SeckillPromotionServiceImpl extends ServiceImpl<SeckillPromotionDao, SeckillPromotion> implements SeckillPromotionService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SeckillPromotion> page = this.page(
                new Query<SeckillPromotion>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}