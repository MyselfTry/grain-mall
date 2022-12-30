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

import com.java.gmall.sms.entity.SkuLadder;
import com.java.gmall.sms.service.SkuLadderService;

/**
 * 商品阶梯价格
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Api(tags = "商品阶梯价格 管理")
@RestController
@RequestMapping("sms/skuladder")
public class SkuLadderController {
    @Autowired
    private SkuLadderService skuLadderService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:skuladder:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = skuLadderService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:skuladder:info')")
    public Resp<SkuLadder> info(@PathVariable("id") Long id){
		SkuLadder skuLadder = skuLadderService.getById(id);

        return Resp.ok(skuLadder);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:skuladder:save')")
    public Resp<Object> save(@RequestBody SkuLadder skuLadder){
		skuLadderService.save(skuLadder);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:skuladder:update')")
    public Resp<Object> update(@RequestBody SkuLadder skuLadder){
		skuLadderService.updateById(skuLadder);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:skuladder:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		skuLadderService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
