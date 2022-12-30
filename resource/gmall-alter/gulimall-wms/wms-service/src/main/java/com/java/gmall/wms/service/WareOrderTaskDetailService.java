package com.java.gmall.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.entity.WareOrderTaskDetail;

/**
 * 库存工作单
 *
 * @author jiangli
 * @since  2020-01-11 15:49:45
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetail> {

    PageVo queryPage(QueryCondition params);
}

