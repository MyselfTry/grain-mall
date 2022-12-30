package com.java.gmall.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.dao.FeightTemplateDao;
import com.java.gmall.wms.entity.FeightTemplate;
import com.java.gmall.wms.service.FeightTemplateService;
import org.springframework.stereotype.Service;

@Service("feightTemplateService")
public class FeightTemplateServiceImpl extends ServiceImpl<FeightTemplateDao, FeightTemplate> implements FeightTemplateService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<FeightTemplate> page = this.page(
                new Query<FeightTemplate>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}