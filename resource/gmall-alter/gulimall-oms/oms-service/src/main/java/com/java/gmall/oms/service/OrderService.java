package com.java.gmall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.oms.entity.Order;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.oms.vo.OrderSubmitVO;

/**
 * 订单
 *
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
public interface OrderService extends IService<Order> {

    PageVo queryPage(QueryCondition params);

	Order saveOrder(OrderSubmitVO orderSubmitVO);
}

