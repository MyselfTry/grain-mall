package com.java.gmall.oms.vo;

import com.java.gmall.pms.entity.SkuSaleAttrValue;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemVO {

    private Long skuId;// 商品id
    private String title;// 标题
    private String defaultImage;// 图片
    private BigDecimal price;// 加入购物车时的价格
    private Integer count;// 购买数量
    private List<SkuSaleAttrValue> skuAttrValue;// 商品规格参数
    private BigDecimal weight; // 重量
}