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

import com.java.gmall.ums.entity.MemberLoginLog;
import com.java.gmall.ums.service.MemberLoginLogService;

/**
 * 会员登录记录
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
@Api(tags = "会员登录记录 管理")
@RestController
@RequestMapping("ums/memberloginlog")
public class MemberLoginLogController {
    @Autowired
    private MemberLoginLogService memberLoginLogService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:memberloginlog:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = memberLoginLogService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:memberloginlog:info')")
    public Resp<MemberLoginLog> info(@PathVariable("id") Long id){
		MemberLoginLog memberLoginLog = memberLoginLogService.getById(id);

        return Resp.ok(memberLoginLog);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:memberloginlog:save')")
    public Resp<Object> save(@RequestBody MemberLoginLog memberLoginLog){
		memberLoginLogService.save(memberLoginLog);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:memberloginlog:update')")
    public Resp<Object> update(@RequestBody MemberLoginLog memberLoginLog){
		memberLoginLogService.updateById(memberLoginLog);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:memberloginlog:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		memberLoginLogService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
