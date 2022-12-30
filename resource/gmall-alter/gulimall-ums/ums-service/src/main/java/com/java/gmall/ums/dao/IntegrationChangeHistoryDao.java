package com.java.gmall.ums.dao;

import com.java.gmall.ums.entity.IntegrationChangeHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分变化历史记录
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
@Mapper
public interface IntegrationChangeHistoryDao extends BaseMapper<IntegrationChangeHistory> {
	
}
