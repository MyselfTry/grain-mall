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

import com.java.gmall.oms.entity.PaymentInfo;
import com.java.gmall.oms.service.PaymentInfoService;

/**
 * 支付信息表
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
@Api(tags = "支付信息表 管理")
@RestController
@RequestMapping("oms/paymentinfo")
public class PaymentInfoController {
    @Autowired
    private PaymentInfoService paymentInfoService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oms:paymentinfo:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = paymentInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('oms:paymentinfo:info')")
    public Resp<PaymentInfo> info(@PathVariable("id") Long id){
		PaymentInfo paymentInfo = paymentInfoService.getById(id);

        return Resp.ok(paymentInfo);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('oms:paymentinfo:save')")
    public Resp<Object> save(@RequestBody PaymentInfo paymentInfo){
		paymentInfoService.save(paymentInfo);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('oms:paymentinfo:update')")
    public Resp<Object> update(@RequestBody PaymentInfo paymentInfo){
		paymentInfoService.updateById(paymentInfo);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('oms:paymentinfo:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		paymentInfoService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
