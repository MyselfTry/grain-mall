package com.java.gmall.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.dao.WareOrderTaskDetailDao;
import com.java.gmall.wms.entity.WareOrderTaskDetail;
import com.java.gmall.wms.service.WareOrderTaskDetailService;
import org.springframework.stereotype.Service;

@Service("wareOrderTaskDetailService")
public class WareOrderTaskDetailServiceImpl extends ServiceImpl<WareOrderTaskDetailDao, WareOrderTaskDetail> implements WareOrderTaskDetailService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<WareOrderTaskDetail> page = this.page(
                new Query<WareOrderTaskDetail>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}