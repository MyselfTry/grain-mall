package com.java.gmall.wms.controller;

import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.wms.entity.WareOrderTask;
import com.java.gmall.wms.service.WareOrderTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 库存工作单
 * @author jiangli
 * @since  2020-01-11 15:49:45
 */
@Api(tags = "库存工作单 管理")
@RestController
@RequestMapping("wms/wareordertask")
public class WareOrderTaskController {
    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('wms:wareordertask:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = wareOrderTaskService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('wms:wareordertask:info')")
    public Resp<WareOrderTask> info(@PathVariable("id") Long id){
		WareOrderTask wareOrderTask = wareOrderTaskService.getById(id);

        return Resp.ok(wareOrderTask);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('wms:wareordertask:save')")
    public Resp<Object> save(@RequestBody WareOrderTask wareOrderTask){
		wareOrderTaskService.save(wareOrderTask);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('wms:wareordertask:update')")
    public Resp<Object> update(@RequestBody WareOrderTask wareOrderTask){
		wareOrderTaskService.updateById(wareOrderTask);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('wms:wareordertask:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		wareOrderTaskService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
