package com.java.gmall.pms.service.impl;

import com.java.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.java.gmall.pms.entity.AttrAttrgroupRelation;
import com.java.gmall.pms.service.AttrAttrgroupRelationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelation> implements AttrAttrgroupRelationService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrAttrgroupRelation> page = this.page(
                new Query<AttrAttrgroupRelation>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

	@Override
	@Transactional
	public void deleteByAttrGroupIdAndAttrGroupId(List<AttrAttrgroupRelation> relations) {
		relations.forEach(relationEntity -> {
			this.remove(new QueryWrapper<AttrAttrgroupRelation>()
					.eq("attr_id", relationEntity.getAttrId())
					.eq("attr_group_id", relationEntity.getAttrGroupId())
			);
		});
	}

}