package com.java.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.oms.entity.RefundInfo;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 退款信息
 *
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
public interface RefundInfoService extends IService<RefundInfo> {

    PageVo queryPage(QueryCondition params);
}

