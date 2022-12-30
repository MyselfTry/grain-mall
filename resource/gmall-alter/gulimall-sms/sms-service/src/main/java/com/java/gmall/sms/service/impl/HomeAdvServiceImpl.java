package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.HomeAdvDao;
import com.java.gmall.sms.entity.HomeAdv;
import com.java.gmall.sms.service.HomeAdvService;

@Service("homeAdvService")
public class HomeAdvServiceImpl extends ServiceImpl<HomeAdvDao, HomeAdv> implements HomeAdvService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<HomeAdv> page = this.page(
                new Query<HomeAdv>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}