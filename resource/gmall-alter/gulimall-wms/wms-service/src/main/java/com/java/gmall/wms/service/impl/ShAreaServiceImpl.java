package com.java.gmall.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.dao.ShAreaDao;
import com.java.gmall.wms.entity.ShArea;
import com.java.gmall.wms.service.ShAreaService;
import org.springframework.stereotype.Service;

@Service("shAreaService")
public class ShAreaServiceImpl extends ServiceImpl<ShAreaDao, ShArea> implements ShAreaService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<ShArea> page = this.page(
                new Query<ShArea>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}