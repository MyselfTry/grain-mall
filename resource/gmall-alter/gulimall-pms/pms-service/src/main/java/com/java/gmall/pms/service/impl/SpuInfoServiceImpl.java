package com.java.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.dao.SpuInfoDao;
import com.java.gmall.pms.entity.*;
import com.java.gmall.pms.feign.SkuSaleFeign;
import com.java.gmall.pms.service.*;
import com.java.gmall.pms.vo.ProductAttrValueVO;
import com.java.gmall.pms.vo.SkuInfoVO;
import com.java.gmall.pms.vo.SpuInfoVO;
import com.java.gmall.sms.dto.SkuSaleDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service("spuInfoService")
@Slf4j
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfo> implements SpuInfoService {
	@Autowired
	private SpuInfoDescService spuInfoDescService;
	@Autowired
	private ProductAttrValueService productAttrValueService;
	@Autowired
	private SkuInfoService skuInfoService;
	@Autowired
	private SkuImagesService skuImagesService;
	@Autowired
	private AttrService attrService;
	@Autowired
	private SkuSaleAttrValueService skuSaleAttrValueService;
	@Autowired
	private SkuSaleFeign skuSaleFeign;
	@Autowired
	private AmqpTemplate amqpTemplate;

    @Override
    public PageVo<SpuInfo> queryPage(QueryCondition params) {
        IPage<SpuInfo> page = this.page(
                new Query<SpuInfo>().getPage(params),
                new LambdaQueryWrapper<SpuInfo>().eq(SpuInfo::getPublishStatus,1) // 上架状态
        );

        return new PageVo<>(page);
    }

	@Override
	public PageVo querySpuInfo(QueryCondition condition, Long catId) {
		// 封装分页条件
		IPage<SpuInfo> page = new Query<SpuInfo>().getPage(condition);

		// 封装查询条件
		QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
		// 如果分类id不为0，要根据分类id查，否则查全部
		if (catId != 0){
			wrapper.eq("catalog_id", catId);
		}
		// 如果用户输入了检索条件，根据检索条件查
		String key = condition.getKey();
		if (StringUtils.isNotBlank(key)){
			wrapper.and(t -> t.like("spu_name", key).or().like("id", key));
		}
//		new LambdaQueryWrapper<SpuInfo>().and(e->e.like(SpuInfo::getId,key).or().like(SpuInfo::getSpuName,key)
		return new PageVo(this.page(page, wrapper));
	}

	@Override
	@GlobalTransactional
	public void saveSpuInfoVO(SpuInfoVO spuInfoVO) {
		/// 1.保存spu相关
		// 1.1. 保存spu基本信息 spu_info
		spuInfoVO.setPublishStatus(1); // 默认是已上架
		spuInfoVO.setCreateTime(new Date());
		spuInfoVO.setUpdateTime(spuInfoVO.getCreateTime()); // 新增时，更新时间和创建时间一致
		this.save(spuInfoVO);
		Long spuId = spuInfoVO.getId(); // 获取新增后的spuId

		// 1.2. 保存spu的描述信息 spu_info_desc
		SpuInfoDesc spuInfoDescEntity = new SpuInfoDesc();
		// 注意：spu_info_desc表的主键是spu_id,需要在实体类中配置该主键不是自增主键
		spuInfoDescEntity.setSpuId(spuId);
		// 把商品的图片描述，保存到spu详情中，图片地址以逗号进行分割
		spuInfoDescEntity.setDecript(StringUtils.join(spuInfoVO.getSpuImages(), ","));
		spuInfoDescService.save(spuInfoDescEntity);

		// 1.3. 保存spu的规格参数信息
		List<ProductAttrValueVO> baseAttrs = spuInfoVO.getBaseAttrs();
		if (!CollectionUtils.isEmpty(baseAttrs)) {
			List<ProductAttrValue> productAttrValueEntities = baseAttrs.stream().map(productAttrValueVO -> {
				productAttrValueVO.setSpuId(spuId);
				productAttrValueVO.setAttrSort(0);
				productAttrValueVO.setQuickShow(0);
				return productAttrValueVO;
			}).collect(Collectors.toList());
			this.productAttrValueService.saveBatch(productAttrValueEntities);
		}

		/// 2. 保存sku相关信息
		List<SkuInfoVO> skuInfoVOs = spuInfoVO.getSkus();
		if (CollectionUtils.isEmpty(skuInfoVOs)){
			return;
		}
		skuInfoVOs.forEach(skuInfoVO -> {
			// 2.1. 保存sku基本信息
			SkuInfo skuInfoEntity = new SkuInfo();
			BeanUtils.copyProperties(skuInfoVO, skuInfoEntity);
			// 品牌和分类的id需要从spuInfo中获取
			skuInfoEntity.setBrandId(spuInfoVO.getBrandId());
			skuInfoEntity.setCatalogId(spuInfoVO.getCatalogId());
			// 获取随机的uuid作为sku的编码
			skuInfoEntity.setSkuCode(UUID.randomUUID().toString().substring(0, 10).toUpperCase());
			// 获取图片列表
			List<String> images = skuInfoVO.getImages();
			// 如果图片列表不为null，则设置默认图片
			if (!CollectionUtils.isEmpty(images)){
				// 设置第一张图片作为默认图片
				skuInfoEntity.setSkuDefaultImg(skuInfoEntity.getSkuDefaultImg()==null ? images.get(0) : skuInfoEntity.getSkuDefaultImg());
			}
			skuInfoEntity.setSpuId(spuId);
			skuInfoService.save(skuInfoEntity);
			// 获取skuId
			Long skuId = skuInfoEntity.getSkuId();

			// 2.2. 保存sku图片信息
			if (!CollectionUtils.isEmpty(images)){
				String defaultImage = images.get(0);
				List<SkuImages> skuImageses = images.stream().map(image -> {
					SkuImages skuImagesEntity = new SkuImages();
					skuImagesEntity.setDefaultImg(StringUtils.equals(defaultImage, image) ? 1 : 0);
					skuImagesEntity.setSkuId(skuId);
					skuImagesEntity.setImgSort(0);
					skuImagesEntity.setImgUrl(image);
					return skuImagesEntity;
				}).collect(Collectors.toList());
				skuImagesService.saveBatch(skuImageses);
			}

			// 2.3. 保存sku的规格参数（销售属性）
			List<SkuSaleAttrValue> saleAttrs = skuInfoVO.getSaleAttrs();
			saleAttrs.forEach(saleAttr -> {
				// 设置属性名，需要根据id查询AttrEntity
				saleAttr.setAttrName(attrService.getById(saleAttr.getAttrId()).getAttrName());
				saleAttr.setAttrSort(0);
				saleAttr.setSkuId(skuId);
			});
			skuSaleAttrValueService.saveBatch(saleAttrs);

			// 3. 保存营销相关信息，需要远程调用gmall-sms
			SkuSaleDTO skuSaleDTO = new SkuSaleDTO();
			BeanUtils.copyProperties(skuInfoVO, skuSaleDTO);
			skuSaleDTO.setSkuId(skuId);
			this.skuSaleFeign.saveSkuSaleInfo(skuSaleDTO);
		});

		// 发送mq消息
		sendMessage(spuInfoVO.getId(),"insert");
	}

	@Override
	public void updateSpuInfo(SpuInfo spuInfo) {
		this.baseMapper.updateById(spuInfo);

		// 发送mq消息,Cart模块监听该消息
		sendMessage(spuInfo.getId(),"update");
	}

	/**
	 * 向mq发送消息
	 */
	private void sendMessage(Long id, String type){
		// 发送消息
		try {
			// 这里没有指定交换机，因此默认发送到了配置中的：gmall.item.exchange
			this.amqpTemplate.convertAndSend("item." + type, id);
		} catch (Exception e) {
			log.error("{}商品消息发送异常，商品id：{}", type, id, e);
		}
	}

}