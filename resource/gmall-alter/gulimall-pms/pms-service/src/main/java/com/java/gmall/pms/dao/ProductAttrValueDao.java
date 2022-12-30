package com.java.gmall.pms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.gmall.pms.entity.ProductAttrValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * spu属性值
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValue> {

	List<ProductAttrValue> querySearchAttrValueBySpuId(Long spuId);
	
}
