package com.java.gmall.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.gmall.wms.entity.WareSku;
import com.java.gmall.wms.vo.SkuLockVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 *
 * @author jiangli
 * @since 2020-01-11 15:49:45
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSku> {

	List<WareSku> checkStore(@Param("skuId") Long skuId, @Param("count") Integer count);

	Integer lockStore(@Param("id") Long id, @Param("count") Integer count);

	Integer unLockStore(@Param("id") Long id, @Param("count") Integer count);

	Integer minusStock(@Param("id") Long id, @Param("count") Integer count);
}
