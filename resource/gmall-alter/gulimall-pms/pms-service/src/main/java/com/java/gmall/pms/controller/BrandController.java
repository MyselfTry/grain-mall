package com.java.gmall.pms.controller;

import java.util.Arrays;


import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.Brand;
import com.java.gmall.pms.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;





/**
 * 品牌
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
@Api(tags = "品牌管理")
@RestController
@RequestMapping("pms/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:brand:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = brandService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{brandId}")
    @PreAuthorize("hasAuthority('pms:brand:info')")
    public Resp<Brand> info(@PathVariable("brandId") Long brandId){
		Brand brand = brandService.getById(brandId);

        return Resp.ok(brand);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:brand:save')")
    public Resp<Object> save(@RequestBody Brand brand){
		brandService.save(brand);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:brand:update')")
    public Resp<Object> update(@RequestBody Brand brand){
		brandService.updateById(brand);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public Resp<Object> delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return Resp.ok(null);
    }

}
