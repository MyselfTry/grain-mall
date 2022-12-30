package com.java.gmall.sms.dao;

import com.java.gmall.sms.entity.SeckillSession;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动场次
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Mapper
public interface SeckillSessionDao extends BaseMapper<SeckillSession> {
	
}
