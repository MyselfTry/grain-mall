package com.java.gmall.ums.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.ums.dao.GrowthChangeHistoryDao;
import com.java.gmall.ums.entity.GrowthChangeHistory;
import com.java.gmall.ums.service.GrowthChangeHistoryService;

@Service("growthChangeHistoryService")
public class GrowthChangeHistoryServiceImpl extends ServiceImpl<GrowthChangeHistoryDao, GrowthChangeHistory> implements GrowthChangeHistoryService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<GrowthChangeHistory> page = this.page(
                new Query<GrowthChangeHistory>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}