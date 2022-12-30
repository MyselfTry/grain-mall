package com.java.gmall.pms.controller;

import java.util.Arrays;
import java.util.List;


import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.ProductAttrValue;
import com.java.gmall.pms.service.ProductAttrValueService;
import com.java.gmall.pms.vo.BaseGroupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * spu属性值
 *
 * @author jiangli
 * @since 2020-01-10 04:05:29
 */
@Api(tags = "spu属性值管理")
@RestController
@RequestMapping("pms/productattrvalue")
public class ProductAttrValueController {
	@Autowired
	private ProductAttrValueService productAttrValueService;

	@GetMapping("{spuId}/{cateId}")
	public ResponseEntity<List<BaseGroupVO>> queryAttrGroups(@PathVariable("spuId") Long spuId, @PathVariable("cateId") Long cateId) {
		List<BaseGroupVO> baseGroupVOS = productAttrValueService.queryAttrGroups(spuId, cateId);
		return ResponseEntity.ok(baseGroupVOS);
	}

	@GetMapping("{spuId}")
	public Resp<List<ProductAttrValue>> querySearchAttrValueBySpuId(@PathVariable("spuId") Long spuId) {
		List<ProductAttrValue> productAttrValues = productAttrValueService.querySearchAttrValueBySpuId(spuId);
		return Resp.ok(productAttrValues);
	}

	/**
	 * 列表
	 */
	@ApiOperation("分页查询(排序)")
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('pms:productattrvalue:list')")
	public Resp<PageVo> list(QueryCondition queryCondition) {
		PageVo page = productAttrValueService.queryPage(queryCondition);

		return Resp.ok(page);
	}


	/**
	 * 信息
	 */
	@ApiOperation("详情查询")
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('pms:productattrvalue:info')")
	public Resp<ProductAttrValue> info(@PathVariable("id") Long id) {
		ProductAttrValue productAttrValue = productAttrValueService.getById(id);

		return Resp.ok(productAttrValue);
	}

	/**
	 * 保存
	 */
	@ApiOperation("保存")
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('pms:productattrvalue:save')")
	public Resp<Object> save(@RequestBody ProductAttrValue productAttrValue) {
		productAttrValueService.save(productAttrValue);

		return Resp.ok(null);
	}

	/**
	 * 修改
	 */
	@ApiOperation("修改")
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('pms:productattrvalue:update')")
	public Resp<Object> update(@RequestBody ProductAttrValue productAttrValue) {
		productAttrValueService.updateById(productAttrValue);

		return Resp.ok(null);
	}

	/**
	 * 删除
	 */
	@ApiOperation("删除")
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('pms:productattrvalue:delete')")
	public Resp<Object> delete(@RequestBody Long[] ids) {
		productAttrValueService.removeByIds(Arrays.asList(ids));

		return Resp.ok(null);
	}

}
