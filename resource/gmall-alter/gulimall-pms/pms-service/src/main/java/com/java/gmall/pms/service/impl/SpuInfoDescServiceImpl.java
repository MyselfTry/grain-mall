package com.java.gmall.pms.service.impl;

import com.java.gmall.pms.dao.SpuInfoDescDao;
import com.java.gmall.pms.entity.SpuInfoDesc;
import com.java.gmall.pms.service.SpuInfoDescService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;



@Service("spuInfoDescService")
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescDao, SpuInfoDesc> implements SpuInfoDescService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoDesc> page = this.page(
                new Query<SpuInfoDesc>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}