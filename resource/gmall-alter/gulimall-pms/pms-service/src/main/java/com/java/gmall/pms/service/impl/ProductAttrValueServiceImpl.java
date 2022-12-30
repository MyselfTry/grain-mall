package com.java.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.core.bean.Resp;
import com.java.core.exception.RRException;
import com.java.gmall.pms.dao.ProductAttrValueDao;
import com.java.gmall.pms.entity.AttrAttrgroupRelation;
import com.java.gmall.pms.entity.AttrGroup;
import com.java.gmall.pms.entity.ProductAttrValue;
import com.java.gmall.pms.service.AttrAttrgroupRelationService;
import com.java.gmall.pms.service.AttrGroupService;
import com.java.gmall.pms.service.ProductAttrValueService;
import com.java.gmall.pms.vo.BaseGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import java.util.List;
import java.util.stream.Collectors;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValue> implements ProductAttrValueService {
	@Autowired
	private AttrGroupService attrGroupService;
	@Autowired
	private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<ProductAttrValue> page = this.page(
                new Query<ProductAttrValue>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

	@Override
	public List<ProductAttrValue> querySearchAttrValueBySpuId(Long spuId) {
		if (spuId == null || spuId <= 0) {
			throw new RRException("参数错误");
		}
		return this.baseMapper.querySearchAttrValueBySpuId(spuId);
	}

	@Override
	public List<BaseGroupVO> queryAttrGroups(Long spuId, Long cateId) {
    	// 1.根据分类id找到组id
		List<AttrGroup> attrGroups = attrGroupService.list(new LambdaQueryWrapper<AttrGroup>().eq(AttrGroup::getCatelogId, cateId));

		return attrGroups.stream().map(attrGroup -> {
			BaseGroupVO baseGroupVO = new BaseGroupVO();
			baseGroupVO.setName(attrGroup.getAttrGroupName());
			// 根据组id查询关联表得到attrId
			List<AttrAttrgroupRelation> attrGroupRelations = attrAttrgroupRelationService.list(new LambdaQueryWrapper<AttrAttrgroupRelation>().eq(AttrAttrgroupRelation::getAttrGroupId, attrGroup.getAttrGroupId()));
			List<Long> attrIds = attrGroupRelations.stream().map(AttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
			List<ProductAttrValue> productAttrValues = this.baseMapper.selectList(new LambdaQueryWrapper<ProductAttrValue>().eq(ProductAttrValue::getSpuId, spuId).in(ProductAttrValue::getAttrId, attrIds));
			baseGroupVO.setAttrs(productAttrValues);
			return baseGroupVO;
		}).collect(Collectors.toList());
	}

}