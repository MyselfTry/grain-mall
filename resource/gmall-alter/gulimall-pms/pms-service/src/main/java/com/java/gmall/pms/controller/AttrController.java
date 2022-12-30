package com.java.gmall.pms.controller;

import java.util.Arrays;


import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.Attr;
import com.java.gmall.pms.service.AttrService;
import com.java.gmall.pms.vo.AttrVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



/**
 * 商品属性
 *
 * @author jiangli
 * @since 2020-01-10 04:05:29
 */
@Api(tags = "商品属性管理")
@RestController
@RequestMapping("pms/attr")
public class AttrController {
	@Autowired
	private AttrService attrService;

	@GetMapping
	public Resp<PageVo> queryAttrByCidAndType(QueryCondition queryCondition, @RequestParam("cid") Long cid, @RequestParam("type") Integer type) {
		PageVo page = attrService.queryAttrByCidAndType(queryCondition, cid, type);

		return Resp.ok(page);
	}

	/**
	 * 列表
	 */
	@ApiOperation("分页查询(排序)")
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('pms:attr:list')")
	public Resp<PageVo> list(QueryCondition queryCondition) {
		PageVo page = attrService.queryPage(queryCondition);

		return Resp.ok(page);
	}


	/**
	 * 信息
	 */
	@ApiOperation("详情查询")
	@GetMapping("/info/{attrId}")
	@PreAuthorize("hasAuthority('pms:attr:info')")
	public Resp<Attr> info(@PathVariable("attrId") Long attrId) {
		Attr attr = attrService.getById(attrId);

		return Resp.ok(attr);
	}

	/**
	 * 保存
	 */
	@ApiOperation("保存")
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('pms:attr:save')")
	public Resp<Object> save(@RequestBody AttrVO attrVO) {
		this.attrService.saveAttrVO(attrVO);

		return Resp.ok(null);
	}

	/**
	 * 修改
	 */
	@ApiOperation("修改")
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('pms:attr:update')")
	public Resp<Object> update(@RequestBody Attr attr) {
		attrService.updateById(attr);

		return Resp.ok(null);
	}

	/**
	 * 删除
	 */
	@ApiOperation("删除")
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('pms:attr:delete')")
	public Resp<Object> delete(@RequestBody Long[] attrIds) {
		attrService.removeByIds(Arrays.asList(attrIds));

		return Resp.ok(null);
	}

}
