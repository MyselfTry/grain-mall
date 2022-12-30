package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.MemberPriceDao;
import com.java.gmall.sms.entity.MemberPrice;
import com.java.gmall.sms.service.MemberPriceService;

@Service("memberPriceService")
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceDao, MemberPrice> implements MemberPriceService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<MemberPrice> page = this.page(
                new Query<MemberPrice>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}