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

import com.java.gmall.ums.entity.Member;
import com.java.gmall.ums.service.MemberService;

/**
 * 会员
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
@Api(tags = "会员管理")
@RestController
@RequestMapping("ums/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

	/**
	 * 校验数据是否可用
	 */
	@GetMapping("check/{data}/{type}")
	public Resp<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
		Boolean b = this.memberService.checkData(data, type);

		return Resp.ok(b);
	}

	/**
	 * 注册
	 */
	@PostMapping("register")
	public Resp<Object> register(Member member, @RequestParam("code") String code) {
		this.memberService.register(member, code);

		return Resp.ok(null);
	}

	/**
	 * 根据用户名和密码查询用户
	 */
	@GetMapping("query")
	public Resp<Member> queryUser(@RequestParam("username") String username, @RequestParam("password") String password
	) {
		Member member = this.memberService.queryUser(username, password);

		return Resp.ok(member);
	}

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ums:member:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = memberService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ums:member:info')")
    public Resp<Member> info(@PathVariable("id") Long id){
		Member member = memberService.getById(id);

        return Resp.ok(member);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ums:member:save')")
    public Resp<Object> save(@RequestBody Member member){
		memberService.save(member);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ums:member:update')")
    public Resp<Object> update(@RequestBody Member member){
		memberService.updateById(member);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ums:member:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
