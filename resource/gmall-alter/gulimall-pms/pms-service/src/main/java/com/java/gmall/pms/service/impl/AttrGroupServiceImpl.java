package com.java.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.gmall.pms.dao.AttrGroupDao;
import com.java.gmall.pms.entity.Attr;
import com.java.gmall.pms.entity.AttrAttrgroupRelation;
import com.java.gmall.pms.entity.AttrGroup;
import com.java.gmall.pms.service.AttrAttrgroupRelationService;
import com.java.gmall.pms.service.AttrGroupService;
import com.java.gmall.pms.service.AttrService;
import com.java.gmall.pms.vo.GroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroup> implements AttrGroupService {
	@Autowired
	private AttrAttrgroupRelationService attrAttrgroupRelationService;
	@Autowired
	private AttrService attrService;

	@Override
	public PageVo queryPage(QueryCondition params) {
		IPage<AttrGroup> page = this.page(
				new Query<AttrGroup>().getPage(params),
				new QueryWrapper<>()
		);

		return new PageVo(page);
	}

	@Override
	public PageVo queryGroupByPage(QueryCondition queryCondition, Long catId) {
		IPage<AttrGroup> page = this.page(
				new Query<AttrGroup>().getPage(queryCondition),
				new LambdaQueryWrapper<AttrGroup>().eq(catId != null, AttrGroup::getCatelogId, catId)
		);

		return new PageVo(page);
	}

	@Override
	public GroupVO queryGroupByGid(Long gid) {
		AttrGroup attrGroup = this.baseMapper.selectById(gid);
		GroupVO groupVO = new GroupVO();
		BeanUtils.copyProperties(attrGroup,groupVO);
		// 查询中间表
		List<AttrAttrgroupRelation> relations = attrAttrgroupRelationService.list(new LambdaQueryWrapper<AttrAttrgroupRelation>().eq(AttrAttrgroupRelation::getAttrGroupId, gid));
		if (CollectionUtils.isEmpty(relations)) {
			return groupVO;
		}
		// 获取attrIds
		List<Long> attrIds = relations.stream().map(AttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
		// 查询attrs
		List<Attr> attrs = attrService.list(new LambdaQueryWrapper<Attr>().in(Attr::getAttrId, attrIds));

		groupVO.setAttrEntities(attrs);
		groupVO.setRelations(relations);
		return groupVO;
	}

	@Override
	public List<GroupVO> queryByCid(Long cid) {
		// 查询所有的分组
		List<AttrGroup> attrGroupEntities = this.list(new QueryWrapper<AttrGroup>().eq("catelog_id", cid));

		// 查询出每组下的规格参数
		return attrGroupEntities.stream().map(attrGroupEntity -> this.queryGroupByGid(attrGroupEntity.getAttrGroupId())).collect(Collectors.toList());
	}

}