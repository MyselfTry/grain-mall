package com.java.gmall.sms.dao;

import com.java.gmall.sms.entity.SeckillSkuRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动商品关联
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Mapper
public interface SeckillSkuRelationDao extends BaseMapper<SeckillSkuRelation> {
	
}
