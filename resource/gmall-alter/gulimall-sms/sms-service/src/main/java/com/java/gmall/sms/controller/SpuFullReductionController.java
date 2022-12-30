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

import com.java.gmall.sms.entity.SpuFullReduction;
import com.java.gmall.sms.service.SpuFullReductionService;

/**
 * 商品满减信息
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Api(tags = "商品满减信息 管理")
@RestController
@RequestMapping("sms/spufullreduction")
public class SpuFullReductionController {
    @Autowired
    private SpuFullReductionService spuFullReductionService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:spufullreduction:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = spuFullReductionService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:spufullreduction:info')")
    public Resp<SpuFullReduction> info(@PathVariable("id") Long id){
		SpuFullReduction spuFullReduction = spuFullReductionService.getById(id);

        return Resp.ok(spuFullReduction);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:spufullreduction:save')")
    public Resp<Object> save(@RequestBody SpuFullReduction spuFullReduction){
		spuFullReductionService.save(spuFullReduction);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:spufullreduction:update')")
    public Resp<Object> update(@RequestBody SpuFullReduction spuFullReduction){
		spuFullReductionService.updateById(spuFullReduction);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:spufullreduction:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		spuFullReductionService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
