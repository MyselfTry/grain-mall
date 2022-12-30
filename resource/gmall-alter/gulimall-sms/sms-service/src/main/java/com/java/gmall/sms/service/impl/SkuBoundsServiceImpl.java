package com.java.gmall.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.gmall.sms.dto.SkuSaleDTO;
import com.java.gmall.sms.entity.SkuFullReduction;
import com.java.gmall.sms.entity.SkuLadder;
import com.java.gmall.sms.service.SkuFullReductionService;
import com.java.gmall.sms.service.SkuLadderService;
import com.java.gmall.sms.vo.SaleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import com.java.gmall.sms.dao.SkuBoundsDao;
import com.java.gmall.sms.entity.SkuBounds;
import com.java.gmall.sms.service.SkuBoundsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("skuBoundsService")
public class SkuBoundsServiceImpl extends ServiceImpl<SkuBoundsDao, SkuBounds> implements SkuBoundsService {
	@Autowired
	private SkuFullReductionService skuFullReductionService;
	@Autowired
	private SkuLadderService skuLadderService;

	@Override
	public PageVo queryPage(QueryCondition params) {
		IPage<SkuBounds> page = this.page(
				new Query<SkuBounds>().getPage(params),
				new QueryWrapper<>()
		);

		return new PageVo(page);
	}

	@Override
	@Transactional
	public void saveSkuSaleInfo(SkuSaleDTO skuSaleDTO) {
		// 3.1. 积分优惠
		SkuBounds skuBoundsEntity = new SkuBounds();
		BeanUtils.copyProperties(skuSaleDTO, skuBoundsEntity);
		// 数据库保存的是整数0-15，页面绑定是0000-1111
		List<Integer> work = skuSaleDTO.getWork();
		if (!CollectionUtils.isEmpty(work)) {
			skuBoundsEntity.setWork(work.get(0) * 8 + work.get(1) * 4 + work.get(2) * 2 + work.get(3));
		}
		this.save(skuBoundsEntity);

		// 3.2. 满减优惠
		SkuFullReduction skuFullReductionEntity = new SkuFullReduction();
		BeanUtils.copyProperties(skuSaleDTO, skuFullReductionEntity);
		skuFullReductionEntity.setAddOther(skuSaleDTO.getFullAddOther());
		skuFullReductionService.save(skuFullReductionEntity);

		// 3.3. 数量折扣
		SkuLadder skuLadderEntity = new SkuLadder();
		BeanUtils.copyProperties(skuSaleDTO, skuLadderEntity);
		skuLadderService.save(skuLadderEntity);
	}

	@Override
	public List<SaleVO> querySaleBySkuId(Long skuId) {
		List<SaleVO> saleVOS = new ArrayList<>();
		// 查询积分
		SkuBounds skuBounds = this.baseMapper.selectOne(new LambdaQueryWrapper<SkuBounds>().eq(SkuBounds::getSkuId, skuId));
		if (skuBounds != null) {
			SaleVO saleVO = new SaleVO();
			saleVO.setType("积分");
			saleVO.setName("成长积分送" + skuBounds.getGrowBounds() + ",赠送积分" + skuBounds.getBuyBounds());
			saleVOS.add(saleVO);
		}
		// 查询打折
		SkuLadder skuLadder = skuLadderService.getOne(new LambdaQueryWrapper<SkuLadder>().eq(SkuLadder::getSkuId, skuBounds));
		if (skuLadder != null) {
			SaleVO saleVO = new SaleVO();
			saleVO.setType("打折");
			saleVO.setName("满"+skuLadder.getFullCount()+"件,打"+skuLadder.getDiscount().divide(new BigDecimal("10"))+"折");
			saleVOS.add(saleVO);
		}
		// 查询满减
		SkuFullReduction skuFullReduction = skuFullReductionService.getOne(new LambdaQueryWrapper<SkuFullReduction>().eq(SkuFullReduction::getSkuId, skuBounds));
		if (skuFullReduction != null) {
			SaleVO saleVO = new SaleVO();
			saleVO.setType("满减");
			saleVO.setName("满"+skuFullReduction.getFullPrice()+"减"+skuFullReduction.getReducePrice());
			saleVOS.add(saleVO);
		}

		return saleVOS;
	}

}