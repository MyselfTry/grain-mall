package com.java.gmall.pms.api;

import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.*;
import com.java.gmall.pms.vo.BaseGroupVO;
import com.java.gmall.pms.vo.CategoryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/13 22:18
 */
public interface GmallPmsApi {

	/**
	 * 分页查询spu
	 */
	@PostMapping("pms/spuinfo/page")
	Resp<List<SpuInfo>> page(@RequestBody QueryCondition queryCondition);

	/**
	 * 根据spuId查询spu下的sku (skuId pic price title brandId)
	 */
	@GetMapping("pms/skuinfo/{spuId}")
	Resp<List<SkuInfo>> querySkuBySpuId(@PathVariable("spuId") Long spuId);

	/**
	 * 根据brandId查询brand
	 */
	@GetMapping("pms/brand/info/{brandId}")
	Resp<Brand> queryBrandById(@PathVariable("brandId") Long brandId);

	/**
	 * 根据categoryId查询category
	 */
	@GetMapping("pms/category/info/{catId}")
	Resp<Category> queryCategoryById(@PathVariable("catId") Long catId);

	/**
	 * 根据spuId查询搜索属性和值
	 */
	@GetMapping("pms/productattrvalue/{spuId}")
	Resp<List<ProductAttrValue>> querySearchAttrValueBySpuId(@PathVariable("spuId") Long spuId);

	/**
	 * 根据spuId查询spuInfo
	 */
	@GetMapping("pms/spuinfo/info/{id}")
	Resp<SpuInfo> querySpuInfoBySpuId(@PathVariable("id") Long id);

	/**
	 * 查询一级分类
	 */
	@GetMapping("pms/category")
	Resp<List<Category>> queryCategoryByPidOrLevel(@RequestParam(value = "parentCid", required = false) Long parentCid, @RequestParam(value = "level", defaultValue = "0") Integer level);

	/**
	 * 根据父id查询分类信息
	 */
	@GetMapping("pms/category/{pid}")
	List<CategoryVO> queryCategoryVO(@PathVariable("pid") Long pid);

	/**
	 * 根据skuId查询sku
	 */
	@GetMapping("pms/skuinfo/info/{skuId}")
	SkuInfo querySkuBySkuId(@PathVariable("skuId") Long skuId);

	/**
	 * 根据skuId查询sku图片列表
	 */
	@GetMapping("pms/skuimages/{skuId}")
	List<String> querySkuImagesBySkuId(@PathVariable("skuId") Long skuId);

	/**
	 * 根据spuId查询sku所有的销售属性
	 */
	@GetMapping("pms/skusaleattrvalue/{spuId}")
	List<SkuSaleAttrValue> querySkuSaleAttrValueBySpuId(@PathVariable("spuId") Long spuId);

	/**
	 * 根据spuId查询spu详情
	 */
	@GetMapping("pms/spuinfodesc/info/{spuId}")
	Resp<SpuInfoDesc> querySpuDescBySpuId(@PathVariable("spuId") Long spuId);

	/**
	 * 根据spuId和cateId查询组及组下规格参数
	 */
	@GetMapping("pms/productattrvalue/{spuId}/{cateId}")
	List<BaseGroupVO> queryAttrGroups(@PathVariable("spuId") Long spuId, @PathVariable("cateId") Long cateId);

	/**
	 * 根据skuId查询sku销售属性
	 */
	@GetMapping("pms/skusaleattrvalue/sku/{skuId}")
	List<SkuSaleAttrValue> querySkuSaleAttrValueBySkuId(@PathVariable("skuId") Long skuId);
}
