package com.java.core.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.core.utils.SQLFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询参数
 */
public class Query<T> {

    public IPage<T> getPage(QueryCondition params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(QueryCondition params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (params.getPage() != null) {
            curPage = params.getPage();
        }
        if (params.getLimit() != null) {
            limit = params.getLimit();
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject(params.getSidx());
        String order = params.getOrder();


        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (params.getAsc().equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}