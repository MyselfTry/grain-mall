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

import com.java.gmall.sms.entity.CouponHistory;
import com.java.gmall.sms.service.CouponHistoryService;

/**
 * 优惠券领取历史记录
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Api(tags = "优惠券领取历史记录 管理")
@RestController
@RequestMapping("sms/couponhistory")
public class CouponHistoryController {
    @Autowired
    private CouponHistoryService couponHistoryService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:couponhistory:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = couponHistoryService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:couponhistory:info')")
    public Resp<CouponHistory> info(@PathVariable("id") Long id){
		CouponHistory couponHistory = couponHistoryService.getById(id);

        return Resp.ok(couponHistory);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:couponhistory:save')")
    public Resp<Object> save(@RequestBody CouponHistory couponHistory){
		couponHistoryService.save(couponHistory);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:couponhistory:update')")
    public Resp<Object> update(@RequestBody CouponHistory couponHistory){
		couponHistoryService.updateById(couponHistory);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:couponhistory:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		couponHistoryService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
