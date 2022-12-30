package com.java.gmall.pms.service.impl;

import com.java.gmall.pms.dao.SkuInfoDao;
import com.java.gmall.pms.entity.SkuInfo;
import com.java.gmall.pms.service.SkuInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;



@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfo> implements SkuInfoService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuInfo> page = this.page(
                new Query<SkuInfo>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}