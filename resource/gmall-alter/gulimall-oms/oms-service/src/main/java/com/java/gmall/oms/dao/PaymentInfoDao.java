package com.java.gmall.oms.dao;

import com.java.gmall.oms.entity.PaymentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfo> {
	
}
