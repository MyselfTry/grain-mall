package com.java.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.gmall.pms.dao.SkuSaleAttrValueDao;
import com.java.gmall.pms.entity.SkuInfo;
import com.java.gmall.pms.entity.SkuSaleAttrValue;
import com.java.gmall.pms.service.SkuInfoService;
import com.java.gmall.pms.service.SkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import java.util.List;
import java.util.stream.Collectors;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValue> implements SkuSaleAttrValueService {
	@Autowired
	private SkuInfoService skuInfoService;

	@Override
	public PageVo queryPage(QueryCondition params) {
		IPage<SkuSaleAttrValue> page = this.page(
				new Query<SkuSaleAttrValue>().getPage(params),
				new QueryWrapper<>()
		);

		return new PageVo(page);
	}

	@Override
	public List<SkuSaleAttrValue> querySkuSaleAttrValueBySkuId(Long spuId) {
		// 查询spu下的所有sku
		List<SkuInfo> skuInfos = skuInfoService.list(new QueryWrapper<SkuInfo>().eq("spu_id", spuId));
		List<Long> skuIds = skuInfos.stream().map(SkuInfo::getSkuId).collect(Collectors.toList());

		List<SkuSaleAttrValue> skuSaleAttrValues = this.baseMapper.selectList(new LambdaQueryWrapper<SkuSaleAttrValue>().in(SkuSaleAttrValue::getSkuId, skuIds));
		return skuSaleAttrValues;
	}

}