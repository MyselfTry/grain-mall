package com.java.gmall.ums.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.ums.dao.MemberStatisticsInfoDao;
import com.java.gmall.ums.entity.MemberStatisticsInfo;
import com.java.gmall.ums.service.MemberStatisticsInfoService;

@Service("memberStatisticsInfoService")
public class MemberStatisticsInfoServiceImpl extends ServiceImpl<MemberStatisticsInfoDao, MemberStatisticsInfo> implements MemberStatisticsInfoService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<MemberStatisticsInfo> page = this.page(
                new Query<MemberStatisticsInfo>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}