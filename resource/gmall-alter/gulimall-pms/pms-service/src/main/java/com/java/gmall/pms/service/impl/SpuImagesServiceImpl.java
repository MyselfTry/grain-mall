package com.java.gmall.pms.service.impl;

import com.java.gmall.pms.dao.SpuImagesDao;
import com.java.gmall.pms.entity.SpuImages;
import com.java.gmall.pms.service.SpuImagesService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;



@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImages> implements SpuImagesService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuImages> page = this.page(
                new Query<SpuImages>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}