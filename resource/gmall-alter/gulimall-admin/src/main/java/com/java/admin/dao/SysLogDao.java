package com.java.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.admin.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
