package com.java.gmall.pms.vo;

import com.java.gmall.pms.entity.SkuInfo;
import com.java.gmall.pms.entity.SkuSaleAttrValue;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuInfoVO extends SkuInfo {

    private List<String> images;

    // 积分相关的字段
    private BigDecimal growBounds;
    private BigDecimal buyBounds;
    private List<Integer> work;

    // 满减相关的字段
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer fullAddOther;

    // 打折相关的字段
    private Integer fullCount;
    private BigDecimal discount;
    private Integer ladderAddOther;

    // 销售属性
    private List<SkuSaleAttrValue> saleAttrs;
}