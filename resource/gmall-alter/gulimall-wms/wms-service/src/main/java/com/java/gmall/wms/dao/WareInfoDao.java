package com.java.gmall.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.gmall.wms.entity.WareInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库信息
 * @author jiangli
 * @since  2020-01-11 15:49:45
 */
@Mapper
public interface WareInfoDao extends BaseMapper<WareInfo> {
	
}
