package com.java.gmall.sms.controller;

import java.util.Arrays;

import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.java.gmall.sms.entity.SkuFullReduction;
import com.java.gmall.sms.service.SkuFullReductionService;

/**
 * 商品满减信息
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Api(tags = "商品满减信息 管理")
@RestController
@RequestMapping("sms/skufullreduction")
public class SkuFullReductionController {
    @Autowired
    private SkuFullReductionService skuFullReductionService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:skufullreduction:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = skuFullReductionService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:skufullreduction:info')")
    public Resp<SkuFullReduction> info(@PathVariable("id") Long id){
		SkuFullReduction skuFullReduction = skuFullReductionService.getById(id);

        return Resp.ok(skuFullReduction);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:skufullreduction:save')")
    public Resp<Object> save(@RequestBody SkuFullReduction skuFullReduction){
		skuFullReductionService.save(skuFullReduction);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:skufullreduction:update')")
    public Resp<Object> update(@RequestBody SkuFullReduction skuFullReduction){
		skuFullReductionService.updateById(skuFullReduction);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:skufullreduction:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		skuFullReductionService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
