package com.java.gmall.search;

import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.*;
import com.java.gmall.search.entity.Goods;
import com.java.gmall.search.entity.SearchAttr;
import com.java.gmall.search.feign.GmallPmsFeign;
import com.java.gmall.search.repository.GoodsRepository;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangli
 * @since 2020/1/13 21:03
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticTest {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private GmallPmsFeign gmallPmsFeign;
	@Autowired
	private GoodsRepository goodsRepository;

	@Test
	public void importData() {
		elasticsearchTemplate.createIndex(Goods.class);
		elasticsearchTemplate.putMapping(Goods.class);

		Long pageSize = 100l;
		Long pageNum = 1l;

		do {
			// 分页查询已上架商品，即spu中publish_status=1的商品
			QueryCondition queryCondition = new QueryCondition();
			queryCondition.setPage(pageNum);
			queryCondition.setLimit(pageSize);
			Resp<List<SpuInfo>> resp = gmallPmsFeign.page(queryCondition);
			if (resp == null || CollectionUtils.isEmpty(resp.getData())) {
				break;
			}
			List<SpuInfo> spus = resp.getData();
			// 遍历spu, 查询sku
			//spu==>goods
			List<Goods> goodsList = spus.stream().map(e -> {
				Goods goods = new Goods();
				try {
					goods = this.buildGoods(e);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return goods;
			}).collect(Collectors.toList());

			//保存到elasticsearch
			goodsRepository.saveAll(goodsList);

			pageSize = Long.valueOf(spus.size());
			pageNum++;
		} while (pageSize == 100); // 当前页记录数不等于100，则退出循环
	}

	private Goods buildGoods(SpuInfo spuInfo) {
		Goods goods = new Goods();
		Resp<List<SkuInfo>> listResp = gmallPmsFeign.querySkuBySpuId(spuInfo.getId());
		List<SkuInfo> skus = listResp.getData();
		skus.forEach(skuInfo -> {
			goods.setSkuId(skuInfo.getSkuId());
			//查询搜索属性
			Resp<List<ProductAttrValue>> attrValueBySpuId = gmallPmsFeign.querySearchAttrValueBySpuId(spuInfo.getId());
			List<ProductAttrValue> attrValueBySpuIdData = attrValueBySpuId.getData();
			// lambda的anyMatch使用
			// boolean b = attrValueBySpuIdData.stream().anyMatch(e -> e.getQuickShow() > 0);
			if (!org.springframework.util.CollectionUtils.isEmpty(attrValueBySpuIdData)) {
				List<SearchAttr> searchAttrs = attrValueBySpuIdData.stream().map(e -> {
					SearchAttr searchAttr = new SearchAttr();
					searchAttr.setAttrId(e.getAttrId());
					searchAttr.setAttrName(e.getAttrName());
					searchAttr.setAttrValue(e.getAttrValue());
					return searchAttr;
				}).collect(Collectors.toList());
				goods.setAttrs(searchAttrs);
			}
			goods.setBrandId(spuInfo.getBrandId());
			//查询品牌名称
			Resp<Brand> brandResp = gmallPmsFeign.queryBrandById(skuInfo.getBrandId());
			if (brandResp.getData() != null) {
				goods.setBrandName(brandResp.getData().getName());
			}
			goods.setCategoryId(spuInfo.getCatalogId());
			//查询分类名称
			Resp<Category> categoryResp = gmallPmsFeign.queryCategoryById(skuInfo.getCatalogId());
			if (categoryResp.getData() != null) {
				goods.setCategoryName(categoryResp.getData().getName());
			}

			goods.setCreateTime(spuInfo.getCreateTime());
			goods.setPic(skuInfo.getSkuDefaultImg());
			goods.setPrice(skuInfo.getPrice().doubleValue());
			goods.setSale(0L);
			goods.setTitle(skuInfo.getSkuTitle());

		});

		return goods;
	}

}
