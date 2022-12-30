package com.java.gmall.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.dao.WareInfoDao;
import com.java.gmall.wms.entity.WareInfo;
import com.java.gmall.wms.service.WareInfoService;
import org.springframework.stereotype.Service;

@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfo> implements WareInfoService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<WareInfo> page = this.page(
                new Query<WareInfo>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}