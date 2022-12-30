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

import com.java.gmall.ums.entity.MemberCollectSubject;
import com.java.gmall.ums.service.MemberCollectSubjectService;

/**
 * 会员收藏的专题活动
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
@Api(tags = "会员收藏的专题活动 管理")
@RestController
@RequestMapping("ums/membercollectsubject")
public class MemberCollectSubjectController {
    @Autowired
    private MemberCollectSubjectService memberCollectSubjectService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:membercollectsubject:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = memberCollectSubjectService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:membercollectsubject:info')")
    public Resp<MemberCollectSubject> info(@PathVariable("id") Long id){
		MemberCollectSubject memberCollectSubject = memberCollectSubjectService.getById(id);

        return Resp.ok(memberCollectSubject);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:membercollectsubject:save')")
    public Resp<Object> save(@RequestBody MemberCollectSubject memberCollectSubject){
		memberCollectSubjectService.save(memberCollectSubject);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:membercollectsubject:update')")
    public Resp<Object> update(@RequestBody MemberCollectSubject memberCollectSubject){
		memberCollectSubjectService.updateById(memberCollectSubject);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:membercollectsubject:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		memberCollectSubjectService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
