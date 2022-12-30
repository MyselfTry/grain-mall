package com.java.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.oms.entity.OrderReturnReason;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 退货原因
 *
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
public interface OrderReturnReasonService extends IService<OrderReturnReason> {

    PageVo queryPage(QueryCondition params);
}

