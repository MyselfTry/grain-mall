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

import com.java.gmall.oms.entity.OrderReturnReason;
import com.java.gmall.oms.service.OrderReturnReasonService;

/**
 * 退货原因
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
@Api(tags = "退货原因 管理")
@RestController
@RequestMapping("oms/orderreturnreason")
public class OrderReturnReasonController {
    @Autowired
    private OrderReturnReasonService orderReturnReasonService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oms:orderreturnreason:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = orderReturnReasonService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('oms:orderreturnreason:info')")
    public Resp<OrderReturnReason> info(@PathVariable("id") Long id){
		OrderReturnReason orderReturnReason = orderReturnReasonService.getById(id);

        return Resp.ok(orderReturnReason);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('oms:orderreturnreason:save')")
    public Resp<Object> save(@RequestBody OrderReturnReason orderReturnReason){
		orderReturnReasonService.save(orderReturnReason);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('oms:orderreturnreason:update')")
    public Resp<Object> update(@RequestBody OrderReturnReason orderReturnReason){
		orderReturnReasonService.updateById(orderReturnReason);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('oms:orderreturnreason:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		orderReturnReasonService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
