package com.java.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.entity.SkuSaleAttrValue;

import java.util.List;


/**
 * sku销售属性&值
 *
 * @author jiangli
 * @since 2020-01-10 04:05:29
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValue> {

	PageVo queryPage(QueryCondition params);

	List<SkuSaleAttrValue> querySkuSaleAttrValueBySkuId(Long spuId);
}

