package com.java.gmall.ums.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.ums.dao.MemberCollectSubjectDao;
import com.java.gmall.ums.entity.MemberCollectSubject;
import com.java.gmall.ums.service.MemberCollectSubjectService;

@Service("memberCollectSubjectService")
public class MemberCollectSubjectServiceImpl extends ServiceImpl<MemberCollectSubjectDao, MemberCollectSubject> implements MemberCollectSubjectService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<MemberCollectSubject> page = this.page(
                new Query<MemberCollectSubject>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}