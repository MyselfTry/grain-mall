package com.java.gmall.oms.controller;

import java.util.Arrays;

import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.java.gmall.oms.entity.RefundInfo;
import com.java.gmall.oms.service.RefundInfoService;

/**
 * 退款信息
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
@Api(tags = "退款信息 管理")
@RestController
@RequestMapping("oms/refundinfo")
public class RefundInfoController {
    @Autowired
    private RefundInfoService refundInfoService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oms:refundinfo:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = refundInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('oms:refundinfo:info')")
    public Resp<RefundInfo> info(@PathVariable("id") Long id){
		RefundInfo refundInfo = refundInfoService.getById(id);

        return Resp.ok(refundInfo);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('oms:refundinfo:save')")
    public Resp<Object> save(@RequestBody RefundInfo refundInfo){
		refundInfoService.save(refundInfo);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('oms:refundinfo:update')")
    public Resp<Object> update(@RequestBody RefundInfo refundInfo){
		refundInfoService.updateById(refundInfo);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('oms:refundinfo:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		refundInfoService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
