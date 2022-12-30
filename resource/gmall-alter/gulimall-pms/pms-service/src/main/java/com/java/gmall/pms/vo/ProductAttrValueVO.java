package com.java.gmall.pms.vo;

import com.java.gmall.pms.entity.ProductAttrValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ProductAttrValueVO extends ProductAttrValue {

    public void setValueSelected(List<Object> valueSelected){
        // 如果接受的集合为空，则不设置
        if (CollectionUtils.isEmpty(valueSelected)){
            return;
        }
        this.setAttrValue(StringUtils.join(valueSelected, ","));
    }
}