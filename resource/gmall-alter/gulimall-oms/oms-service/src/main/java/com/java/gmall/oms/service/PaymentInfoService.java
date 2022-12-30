package com.java.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.oms.entity.PaymentInfo;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 支付信息表
 *
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
public interface PaymentInfoService extends IService<PaymentInfo> {

    PageVo queryPage(QueryCondition params);
}

