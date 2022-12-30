package com.java.gmall.oms.dao;

import com.java.gmall.oms.entity.OrderSetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSetting> {
	
}
