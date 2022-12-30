package com.java.gmall.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.dao.WareOrderTaskDao;
import com.java.gmall.wms.entity.WareOrderTask;
import com.java.gmall.wms.service.WareOrderTaskService;
import org.springframework.stereotype.Service;

@Service("wareOrderTaskService")
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskDao, WareOrderTask> implements WareOrderTaskService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<WareOrderTask> page = this.page(
                new Query<WareOrderTask>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

}