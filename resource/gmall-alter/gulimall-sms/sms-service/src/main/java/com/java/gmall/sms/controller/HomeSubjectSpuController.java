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

import com.java.gmall.sms.entity.HomeSubjectSpu;
import com.java.gmall.sms.service.HomeSubjectSpuService;

/**
 * 专题商品
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Api(tags = "专题商品 管理")
@RestController
@RequestMapping("sms/homesubjectspu")
public class HomeSubjectSpuController {
    @Autowired
    private HomeSubjectSpuService homeSubjectSpuService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:homesubjectspu:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = homeSubjectSpuService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:homesubjectspu:info')")
    public Resp<HomeSubjectSpu> info(@PathVariable("id") Long id){
		HomeSubjectSpu homeSubjectSpu = homeSubjectSpuService.getById(id);

        return Resp.ok(homeSubjectSpu);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:homesubjectspu:save')")
    public Resp<Object> save(@RequestBody HomeSubjectSpu homeSubjectSpu){
		homeSubjectSpuService.save(homeSubjectSpu);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:homesubjectspu:update')")
    public Resp<Object> update(@RequestBody HomeSubjectSpu homeSubjectSpu){
		homeSubjectSpuService.updateById(homeSubjectSpu);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:homesubjectspu:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		homeSubjectSpuService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
