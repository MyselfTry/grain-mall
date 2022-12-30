package com.java.gmall.ums.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.ums.dao.IntegrationChangeHistoryDao;
import com.java.gmall.ums.entity.IntegrationChangeHistory;
import com.java.gmall.ums.service.IntegrationChangeHistoryService;

@Service("integrationChangeHistoryService")
public class IntegrationChangeHistoryServiceImpl extends ServiceImpl<IntegrationChangeHistoryDao, IntegrationChangeHistory> implements IntegrationChangeHistoryService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<IntegrationChangeHistory> page = this.page(
                new Query<IntegrationChangeHistory>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}