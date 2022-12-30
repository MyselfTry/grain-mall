package com.java.gmall.pms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.SkuImages;
import com.java.gmall.pms.service.SkuImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;





/**
 * sku图片
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
@Api(tags = "sku图片管理")
@RestController
@RequestMapping("pms/skuimages")
public class SkuImagesController {
    @Autowired
    private SkuImagesService skuImagesService;

	/**
	 * 根据skuId查询sku图片列表
	 */
	@GetMapping("{skuId}")
	public List<String> querySkuImagesBySkuId(@PathVariable("skuId") Long skuId) {
		List<SkuImages> list = skuImagesService.list(new LambdaQueryWrapper<SkuImages>().eq(SkuImages::getSkuId, skuId));
		return list.stream().map(SkuImages::getImgUrl).collect(Collectors.toList());
	}


	/**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:skuimages:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = skuImagesService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:skuimages:info')")
    public Resp<SkuImages> info(@PathVariable("id") Long id){
		SkuImages skuImages = skuImagesService.getById(id);

        return Resp.ok(skuImages);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:skuimages:save')")
    public Resp<Object> save(@RequestBody SkuImages skuImages){
		skuImagesService.save(skuImages);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:skuimages:update')")
    public Resp<Object> update(@RequestBody SkuImages skuImages){
		skuImagesService.updateById(skuImages);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:skuimages:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		skuImagesService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
