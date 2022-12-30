package com.java.gmall.item.service;

import com.java.core.bean.Resp;
import com.java.core.exception.RRException;
import com.java.gmall.item.feign.PmsFeign;
import com.java.gmall.item.feign.SmsFeign;
import com.java.gmall.item.vo.ItemVO;
import com.java.gmall.pms.entity.*;
import com.java.gmall.pms.vo.BaseGroupVO;
import com.java.gmall.sms.vo.SaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jiangli
 * @since 2020/1/26 13:29
 */
@Service
public class ItemService {
	@Autowired
	private PmsFeign pmsFeign;
	@Autowired
	private SmsFeign smsFeign;
	@Autowired
	private ThreadPoolExecutor threadPoolExecutor;

	public ItemVO queryItem(Long skuId) {
		ItemVO itemVO = new ItemVO();

		/*异步编排*/
		// supplyAsync 有返回值
		CompletableFuture<SkuInfo> skuInfoCompletableFuture = CompletableFuture.supplyAsync(() -> {
			// 根据skuId查询sku
			SkuInfo skuInfo = pmsFeign.querySkuBySkuId(skuId);
			if (skuId == null) {
				throw new RRException("商品不存在");
			}
			itemVO.setSkuId(skuId);
			itemVO.setSkuTitle(skuInfo.getSkuTitle());
			itemVO.setSkuSubtitle(skuInfo.getSkuSubtitle());
			itemVO.setPrice(skuInfo.getPrice());
			itemVO.setWeight(skuInfo.getWeight());
			itemVO.setSpuId(skuInfo.getSpuId());

			return skuInfo;
		}, threadPoolExecutor);


		CompletableFuture<Void> spuCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {

			// 根据spuId查询spu
			Long spuId = skuInfo.getSpuId();
			Resp<SpuInfo> spuInfoResp = pmsFeign.querySpuInfoBySpuId(spuId);
			SpuInfo spuInfo = spuInfoResp.getData();
			itemVO.setSpuName(spuInfo.getSpuName());

		});

		// runAsync 创建一个新的,没有返回值
		CompletableFuture<Void> picsCompletableFuture = CompletableFuture.runAsync(() -> {
			// 根据skuId查询图片列表
			List<String> pics = pmsFeign.querySkuImagesBySkuId(skuId);
			itemVO.setPics(pics);
		}, threadPoolExecutor);

		CompletableFuture<Void> cateCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
			// 分类
			Long catalogId = skuInfo.getCatalogId();
			Resp<Category> categoryResp = pmsFeign.queryCategoryById(catalogId);
			itemVO.setCategory(categoryResp.getData());
		}, threadPoolExecutor);

		CompletableFuture<Void> brandCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
			// 品牌
			Long brandId = skuInfo.getBrandId();
			Resp<Brand> brandResp = pmsFeign.queryBrandById(brandId);
			itemVO.setBrand(brandResp.getData());
		}, threadPoolExecutor);

		CompletableFuture<Void> smsCompletableFuture = CompletableFuture.runAsync(() -> {
			//根据skuId查询营销信息
			List<SaleVO> saleVOS = smsFeign.querySaleBySkuId(skuId);
			itemVO.setSales(saleVOS);
		}, threadPoolExecutor);

		CompletableFuture<Void> attrCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
			// 根据spuId下的skuIds查询所有销售属性
			List<SkuSaleAttrValue> skuSaleAttrValues = pmsFeign.querySkuSaleAttrValueBySpuId(skuInfo.getSpuId());
			itemVO.setSaleAttrs(skuSaleAttrValues);
		});


		CompletableFuture<Void> descCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
			// 商品描述
			Resp<SpuInfoDesc> spuInfoDescResp = pmsFeign.querySpuDescBySpuId(skuInfo.getSpuId());
			SpuInfoDesc spuInfoDesc = spuInfoDescResp.getData();
			if (spuInfoDesc != null) {
				String[] descs = spuInfoDesc.getDecript().split(",");
				itemVO.setDescs(Arrays.asList(descs));
			}
		}, threadPoolExecutor);

		CompletableFuture<Void> groupCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
			// 根据spuId和cateId查询组及组下规格参数
			List<BaseGroupVO> baseGroupVOS = pmsFeign.queryAttrGroups(skuInfo.getSpuId(), skuInfo.getCatalogId());
			itemVO.setAttrGroups(baseGroupVOS);
		}, threadPoolExecutor);

		CompletableFuture.allOf(spuCompletableFuture, picsCompletableFuture, cateCompletableFuture, brandCompletableFuture,
				smsCompletableFuture, attrCompletableFuture, descCompletableFuture, groupCompletableFuture).join();

		return itemVO;
	}
}
