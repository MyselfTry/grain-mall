package com.java.gmall.ums.api;

import com.java.core.bean.Resp;
import com.java.gmall.ums.entity.Member;
import com.java.gmall.ums.entity.MemberReceiveAddress;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/27 22:31
 */
public interface UmsApi {

	/**
	 * 校验数据是否可用
	 */
	@GetMapping("ums/member/check/{data}/{type}")
	Resp<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type);

	/**
	 * 注册
	 */
	@PostMapping("ums/member/register")
	Resp<Object> register(Member member, @RequestParam("code") String code);

	/**
	 * 根据用户名和密码查询用户
	 */
	@GetMapping("ums/member/query")
	Resp<Member> queryUser(@RequestParam("username") String username, @RequestParam("password") String password);

	/**
	 * 根据用户id查询收货地址
	 */
	@GetMapping("ums/memberreceiveaddress/{userId}")
	List<MemberReceiveAddress> queryMemberReceiveAddressByUserId(@PathVariable("userId") Long userId);

	/**
	 * 根据用户id查询用户信息
	 */
	@GetMapping("ums/member/info/{id}")
	Resp<Member> queryUserInfo(@PathVariable("id") Long id);
}
