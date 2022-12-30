package com.java.gmall.search.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author jiangli
 * @since 2020/1/13 20:57
 */
@Data
public class SearchAttr {
	@Field(type = FieldType.Long)
	private Long attrId;

	@Field(type = FieldType.Keyword)
	private String attrName;

	@Field(type = FieldType.Keyword)
	private String attrValue;


}
