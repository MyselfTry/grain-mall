package com.java.gmall.ums.controller;

import java.util.Arrays;

import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.java.gmall.ums.entity.GrowthChangeHistory;
import com.java.gmall.ums.service.GrowthChangeHistoryService;

/**
 * 成长值变化历史记录
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
@Api(tags = "成长值变化历史记录 管理")
@RestController
@RequestMapping("ums/growthchangehistory")
public class GrowthChangeHistoryController {
    @Autowired
    private GrowthChangeHistoryService growthChangeHistoryService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:growthchangehistory:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = growthChangeHistoryService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:growthchangehistory:info')")
    public Resp<GrowthChangeHistory> info(@PathVariable("id") Long id){
		GrowthChangeHistory growthChangeHistory = growthChangeHistoryService.getById(id);

        return Resp.ok(growthChangeHistory);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:growthchangehistory:save')")
    public Resp<Object> save(@RequestBody GrowthChangeHistory growthChangeHistory){
		growthChangeHistoryService.save(growthChangeHistory);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:growthchangehistory:update')")
    public Resp<Object> update(@RequestBody GrowthChangeHistory growthChangeHistory){
		growthChangeHistoryService.updateById(growthChangeHistory);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:growthchangehistory:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		growthChangeHistoryService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
