package com.java.gmall.search.listener;

import cn.hutool.core.collection.CollUtil;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.*;
import com.java.gmall.search.entity.Goods;
import com.java.gmall.search.entity.SearchAttr;
import com.java.gmall.search.feign.GmallPmsFeign;
import com.java.gmall.search.repository.GoodsRepository;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpuInfoListener {
    @Autowired
    private GmallPmsFeign gmallPmsFeign;
    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * 处理insert的消息
     *
     * @param id
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "gmall.item.create.queue", durable = "true"),
            exchange = @Exchange(
                    value = "gmall.item.exchange", /*同pms中的exchange名*/
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}))
    public void listenCreate(Long id) throws Exception {
        if (id == null) {
            return;
        }
	    Resp<List<SkuInfo>> listResp = gmallPmsFeign.querySkuBySpuId(id);
	    List<SkuInfo> skuInfoList = listResp.getData();
	    if (CollUtil.isNotEmpty(skuInfoList)) {
		    Goods goods = new Goods();
		    skuInfoList.forEach(skuInfo -> {
			    goods.setSkuId(skuInfo.getSkuId());
			    //查询搜索属性
			    Resp<List<ProductAttrValue>> attrValueBySpuId = gmallPmsFeign.querySearchAttrValueBySpuId(id);
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
			    goods.setBrandId(skuInfo.getBrandId());
			    //查询品牌名称
			    Resp<Brand> brandResp = gmallPmsFeign.queryBrandById(skuInfo.getBrandId());
			    if (brandResp.getData() != null) {
				    goods.setBrandName(brandResp.getData().getName());
			    }
			    goods.setCategoryId(skuInfo.getCatalogId());
			    //查询分类名称
			    Resp<Category> categoryResp = gmallPmsFeign.queryCategoryById(skuInfo.getCatalogId());
			    if (categoryResp.getData() != null) {
				    goods.setCategoryName(categoryResp.getData().getName());
			    }
			    Resp<SpuInfo> spuInfoResp = gmallPmsFeign.querySpuInfoBySpuId(id);
			    SpuInfo spuInfo = spuInfoResp.getData();
			    goods.setCreateTime(spuInfo.getCreateTime());
			    goods.setPic(skuInfo.getSkuDefaultImg());
			    goods.setPrice(skuInfo.getPrice().doubleValue());
			    goods.setSale(0L);
			    goods.setTitle(skuInfo.getSkuTitle());
			    goodsRepository.save(goods);
		    });

	    }

    }
}