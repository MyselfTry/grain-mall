package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.HomeSubjectDao;
import com.java.gmall.sms.entity.HomeSubject;
import com.java.gmall.sms.service.HomeSubjectService;

@Service("homeSubjectService")
public class HomeSubjectServiceImpl extends ServiceImpl<HomeSubjectDao, HomeSubject> implements HomeSubjectService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<HomeSubject> page = this.page(
                new Query<HomeSubject>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}