package com.java.gmall.pms.controller;

import java.util.Arrays;


import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.CommentReplay;
import com.java.gmall.pms.service.CommentReplayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;





/**
 * 商品评价回复关系
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
@Api(tags = "商品评价回复关系 管理")
@RestController
@RequestMapping("pms/commentreplay")
public class CommentReplayController {
    @Autowired
    private CommentReplayService commentReplayService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:commentreplay:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = commentReplayService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:commentreplay:info')")
    public Resp<CommentReplay> info(@PathVariable("id") Long id){
		CommentReplay commentReplay = commentReplayService.getById(id);

        return Resp.ok(commentReplay);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:commentreplay:save')")
    public Resp<Object> save(@RequestBody CommentReplay commentReplay){
		commentReplayService.save(commentReplay);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:commentreplay:update')")
    public Resp<Object> update(@RequestBody CommentReplay commentReplay){
		commentReplayService.updateById(commentReplay);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:commentreplay:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		commentReplayService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
