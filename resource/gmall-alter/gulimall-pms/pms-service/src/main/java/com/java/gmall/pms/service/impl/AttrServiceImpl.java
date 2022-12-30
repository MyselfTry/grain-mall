package com.java.gmall.pms.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.gmall.pms.dao.AttrDao;
import com.java.gmall.pms.entity.Attr;
import com.java.gmall.pms.entity.AttrAttrgroupRelation;
import com.java.gmall.pms.service.AttrAttrgroupRelationService;
import com.java.gmall.pms.service.AttrService;
import com.java.gmall.pms.vo.AttrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, Attr> implements AttrService {
	@Autowired
	private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<Attr> page = this.page(
                new Query<Attr>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryAttrByCidAndType(QueryCondition queryCondition, Long cid, Integer type) {
	    IPage<Attr> page = this.page(
			    new Query<Attr>().getPage(queryCondition),
			    new LambdaQueryWrapper<Attr>().eq(Attr::getCatelogId,cid).eq(Attr::getAttrType,type)
	    );

	    return new PageVo(page);
    }

	@Transactional
	@Override
	public void saveAttrVO(AttrVO attrVO) {
		// 新增规格参数
		this.baseMapper.insert(attrVO);

		// 新增中间表
		AttrAttrgroupRelation relation = new AttrAttrgroupRelation();
		relation.setAttrId(attrVO.getAttrId());
		relation.setAttrGroupId(attrVO.getAttrGroupId());
		this.attrAttrgroupRelationService.save(relation);
	}

}