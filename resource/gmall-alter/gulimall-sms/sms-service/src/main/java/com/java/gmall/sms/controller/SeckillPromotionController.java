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

import com.java.gmall.sms.entity.SeckillPromotion;
import com.java.gmall.sms.service.SeckillPromotionService;

/**
 * 秒杀活动
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Api(tags = "秒杀活动 管理")
@RestController
@RequestMapping("sms/seckillpromotion")
public class SeckillPromotionController {
    @Autowired
    private SeckillPromotionService seckillPromotionService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:seckillpromotion:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = seckillPromotionService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:seckillpromotion:info')")
    public Resp<SeckillPromotion> info(@PathVariable("id") Long id){
		SeckillPromotion seckillPromotion = seckillPromotionService.getById(id);

        return Resp.ok(seckillPromotion);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:seckillpromotion:save')")
    public Resp<Object> save(@RequestBody SeckillPromotion seckillPromotion){
		seckillPromotionService.save(seckillPromotion);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:seckillpromotion:update')")
    public Resp<Object> update(@RequestBody SeckillPromotion seckillPromotion){
		seckillPromotionService.updateById(seckillPromotion);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:seckillpromotion:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		seckillPromotionService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
