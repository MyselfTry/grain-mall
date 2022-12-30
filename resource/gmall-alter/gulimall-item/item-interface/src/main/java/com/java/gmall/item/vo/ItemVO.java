package com.java.gmall.item.vo;

import com.java.gmall.pms.entity.Brand;
import com.java.gmall.pms.entity.Category;
import com.java.gmall.pms.entity.SkuSaleAttrValue;
import com.java.gmall.pms.entity.SpuInfoDesc;
import com.java.gmall.pms.vo.BaseGroupVO;
import com.java.gmall.sms.vo.SaleVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
/**
 * @author jiangli
 * @since 2020/1/26 13:15
 * 商品详情页
 */

@Data
public class ItemVO {

	private Long skuId;
	private Category category;
	private Brand brand;
	private Long spuId;
	private String spuName;

	private String skuTitle;
	private String skuSubtitle;
	private BigDecimal price;
	private BigDecimal weight;

	//2、sku的所有图片
	private List<String> pics;

	//3、sku的所有促销信息
	private List<SaleVO> sales;

	//4、sku的所有销售属性组合
	private List<SkuSaleAttrValue> saleAttrs;

	//5、spu的所有基本属性
	private List<BaseGroupVO> attrGroups;

	//6、详情介绍
	private List<String> descs;
}