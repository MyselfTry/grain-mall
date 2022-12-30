package com.java.gmall.oms.dao;

import com.java.gmall.oms.entity.OrderOperateHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
@Mapper
public interface OrderOperateHistoryDao extends BaseMapper<OrderOperateHistory> {
	
}
