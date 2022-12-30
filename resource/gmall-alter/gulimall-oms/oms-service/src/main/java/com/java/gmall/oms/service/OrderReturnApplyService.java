package com.java.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.oms.entity.OrderReturnApply;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 订单退货申请
 *
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
public interface OrderReturnApplyService extends IService<OrderReturnApply> {

    PageVo queryPage(QueryCondition params);
}

