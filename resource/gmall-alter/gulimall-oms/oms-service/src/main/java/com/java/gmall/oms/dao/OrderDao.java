package com.java.gmall.oms.dao;

import com.java.gmall.oms.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 订单
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
@Mapper
public interface OrderDao extends BaseMapper<Order> {

	@Update("UPDATE `oms_order` SET `status` = 4 WHERE `order_sn` = #{orderToken} AND `status` = 0")
	Integer closeOrder(String orderToken);

	@Update("UPDATE `oms_order` SET `status` = 1 WHERE `order_sn` = #{orderToken} AND `status` = 0")
	Integer updateOrderStatus(String orderToken);
}
