package com.java.gmall.pms.controller;

import java.util.Arrays;
import java.util.List;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.SkuSaleAttrValue;
import com.java.gmall.pms.service.SkuSaleAttrValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * sku销售属性&值
 *
 * @author jiangli
 * @since 2020-01-10 04:05:29
 */
@Api(tags = "sku销售属性&值管理")
@RestController
@RequestMapping("pms/skusaleattrvalue")
public class SkuSaleAttrValueController {
	@Autowired
	private SkuSaleAttrValueService skuSaleAttrValueService;

	/**
	 * 根据spuId查询sku所有的销售属性
	 */
	@GetMapping("{spuId}")
	public ResponseEntity<List<SkuSaleAttrValue>> querySkuSaleAttrValueBySpuId(@PathVariable("spuId") Long spuId) {
		List<SkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueService.querySkuSaleAttrValueBySkuId(spuId);
		return ResponseEntity.ok(skuSaleAttrValues);
	}

	/**
	 * 根据skuId查询sku销售属性
	 */
	@GetMapping("/sku/{skuId}")
	public ResponseEntity<List<SkuSaleAttrValue>> querySkuSaleAttrValueBySkuId(@PathVariable("skuId") Long skuId) {
		List<SkuSaleAttrValue> skuSaleAttrValues = skuSaleAttrValueService.list(new LambdaQueryWrapper<SkuSaleAttrValue>().eq(SkuSaleAttrValue::getSkuId,skuId));
		return ResponseEntity.ok(skuSaleAttrValues);
	}

	/**
	 * 列表
	 */
	@ApiOperation("分页查询(排序)")
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('pms:skusaleattrvalue:list')")
	public Resp<PageVo> list(QueryCondition queryCondition) {
		PageVo page = skuSaleAttrValueService.queryPage(queryCondition);

		return Resp.ok(page);
	}


	/**
	 * 信息
	 */
	@ApiOperation("详情查询")
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('pms:skusaleattrvalue:info')")
	public Resp<SkuSaleAttrValue> info(@PathVariable("id") Long id) {
		SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueService.getById(id);

		return Resp.ok(skuSaleAttrValue);
	}

	/**
	 * 保存
	 */
	@ApiOperation("保存")
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('pms:skusaleattrvalue:save')")
	public Resp<Object> save(@RequestBody SkuSaleAttrValue skuSaleAttrValue) {
		skuSaleAttrValueService.save(skuSaleAttrValue);

		return Resp.ok(null);
	}

	/**
	 * 修改
	 */
	@ApiOperation("修改")
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('pms:skusaleattrvalue:update')")
	public Resp<Object> update(@RequestBody SkuSaleAttrValue skuSaleAttrValue) {
		skuSaleAttrValueService.updateById(skuSaleAttrValue);

		return Resp.ok(null);
	}

	/**
	 * 删除
	 */
	@ApiOperation("删除")
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('pms:skusaleattrvalue:delete')")
	public Resp<Object> delete(@RequestBody Long[] ids) {
		skuSaleAttrValueService.removeByIds(Arrays.asList(ids));

		return Resp.ok(null);
	}

}
