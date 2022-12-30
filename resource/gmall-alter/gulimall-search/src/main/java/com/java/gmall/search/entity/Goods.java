package com.java.gmall.search.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/13 20:52
 */
@Data
@Document(indexName = "gmall",type = "goods",shards = 1,replicas = 0)
public class Goods {
	@Id
	private Long skuId;

	@Field(type = FieldType.Keyword,index = false)
	private String pic;

	@Field(type = FieldType.Text,analyzer = "ik_max_word")
	private String title;

	@Field(type = FieldType.Double)
	private Double price;

	@Field(type = FieldType.Long)
	private Long sale; // 销量

	@Field(type = FieldType.Date)
	private Date createTime; // 新品

	@Field(type = FieldType.Long)
	private Long brandId;

	@Field(type = FieldType.Keyword)
	private String brandName;

	@Field(type = FieldType.Long)
	private Long categoryId;

	@Field(type = FieldType.Keyword)
	private String categoryName;

	//保存当前sku所有需要检索的属性；
	//检索属性来源于spu的基本属性中的search_type=1（销售属性都已经拼接在标题中了）
	@Field(type = FieldType.Nested)
	private List<SearchAttr> attrs;  // 搜索属性
}
