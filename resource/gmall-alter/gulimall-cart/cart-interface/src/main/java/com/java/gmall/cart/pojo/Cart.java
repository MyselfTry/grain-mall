package com.java.gmall.cart.pojo;

import com.java.gmall.pms.entity.SkuSaleAttrValue;
import com.java.gmall.sms.vo.SaleVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/29 11:16
 */
@Data
public class Cart {

	private Long skuId;// 商品id
	private String title;// 标题
	private String defaultImage;// 图片
	private BigDecimal price;// 加入购物车时的价格
	private BigDecimal currentPrice;// 当前价格
	private Integer count;// 购买数量
	private List<SkuSaleAttrValue> skuAttrValue;// 商品规格参数
	private List<SaleVO> saleVOS;

	private Boolean check;// 是否勾选
}
