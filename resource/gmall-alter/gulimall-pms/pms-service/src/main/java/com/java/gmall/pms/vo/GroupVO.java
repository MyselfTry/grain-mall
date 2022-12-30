package com.java.gmall.pms.vo;

import com.java.gmall.pms.entity.Attr;
import com.java.gmall.pms.entity.AttrAttrgroupRelation;
import com.java.gmall.pms.entity.AttrGroup;
import lombok.Data;

import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/11 13:27
 */
@Data
public class GroupVO extends AttrGroup {

	private List<Attr> attrEntities;

	private List<AttrAttrgroupRelation> relations;
}
