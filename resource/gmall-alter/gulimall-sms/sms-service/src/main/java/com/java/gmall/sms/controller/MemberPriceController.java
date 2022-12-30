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

import com.java.gmall.sms.entity.MemberPrice;
import com.java.gmall.sms.service.MemberPriceService;

/**
 * 商品会员价格
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Api(tags = "商品会员价格 管理")
@RestController
@RequestMapping("sms/memberprice")
public class MemberPriceController {
    @Autowired
    private MemberPriceService memberPriceService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:memberprice:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = memberPriceService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:memberprice:info')")
    public Resp<MemberPrice> info(@PathVariable("id") Long id){
		MemberPrice memberPrice = memberPriceService.getById(id);

        return Resp.ok(memberPrice);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:memberprice:save')")
    public Resp<Object> save(@RequestBody MemberPrice memberPrice){
		memberPriceService.save(memberPrice);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:memberprice:update')")
    public Resp<Object> update(@RequestBody MemberPrice memberPrice){
		memberPriceService.updateById(memberPrice);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:memberprice:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		memberPriceService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
