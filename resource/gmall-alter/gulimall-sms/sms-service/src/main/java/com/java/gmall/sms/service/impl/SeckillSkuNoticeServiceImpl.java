package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.SeckillSkuNoticeDao;
import com.java.gmall.sms.entity.SeckillSkuNotice;
import com.java.gmall.sms.service.SeckillSkuNoticeService;

@Service("seckillSkuNoticeService")
public class SeckillSkuNoticeServiceImpl extends ServiceImpl<SeckillSkuNoticeDao, SeckillSkuNotice> implements SeckillSkuNoticeService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SeckillSkuNotice> page = this.page(
                new Query<SeckillSkuNotice>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}