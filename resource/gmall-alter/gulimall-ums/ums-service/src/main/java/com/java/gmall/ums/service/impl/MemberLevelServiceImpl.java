package com.java.gmall.ums.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.ums.dao.MemberLevelDao;
import com.java.gmall.ums.entity.MemberLevel;
import com.java.gmall.ums.service.MemberLevelService;

@Service("memberLevelService")
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelDao, MemberLevel> implements MemberLevelService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<MemberLevel> page = this.page(
                new Query<MemberLevel>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}