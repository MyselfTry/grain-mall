package com.java.gmall.wms.controller;

import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.wms.entity.WareInfo;
import com.java.gmall.wms.service.WareInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 仓库信息
 * @author jiangli
 * @since  2020-01-11 15:49:45
 */
@Api(tags = "仓库信息 管理")
@RestController
@RequestMapping("wms/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('wms:wareinfo:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = wareInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('wms:wareinfo:info')")
    public Resp<WareInfo> info(@PathVariable("id") Long id){
		WareInfo wareInfo = wareInfoService.getById(id);

        return Resp.ok(wareInfo);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('wms:wareinfo:save')")
    public Resp<Object> save(@RequestBody WareInfo wareInfo){
		wareInfoService.save(wareInfo);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('wms:wareinfo:update')")
    public Resp<Object> update(@RequestBody WareInfo wareInfo){
		wareInfoService.updateById(wareInfo);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('wms:wareinfo:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		wareInfoService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
