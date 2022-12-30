package com.java.gmall.pms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.gmall.pms.entity.SkuInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * sku信息
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
@Mapper
public interface SkuInfoDao extends BaseMapper<SkuInfo> {
	
}
