package com.java.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.entity.AttrAttrgroupRelation;

import java.util.List;


/**
 * 属性&属性分组关联
 *
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelation> {

    PageVo queryPage(QueryCondition params);

	void deleteByAttrGroupIdAndAttrGroupId(List<AttrAttrgroupRelation> relations);
}

