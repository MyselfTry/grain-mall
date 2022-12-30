package com.java.gmall.sms.dao;

import com.java.gmall.sms.entity.MemberPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPrice> {
	
}
