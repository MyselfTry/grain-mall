package com.java.gmall.sms.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.HomeSubjectSpuDao;
import com.java.gmall.sms.entity.HomeSubjectSpu;
import com.java.gmall.sms.service.HomeSubjectSpuService;

@Service("homeSubjectSpuService")
public class HomeSubjectSpuServiceImpl extends ServiceImpl<HomeSubjectSpuDao, HomeSubjectSpu> implements HomeSubjectSpuService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<HomeSubjectSpu> page = this.page(
                new Query<HomeSubjectSpu>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}