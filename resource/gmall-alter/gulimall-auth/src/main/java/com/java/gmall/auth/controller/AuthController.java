package com.java.gmall.auth.controller;

import com.java.core.bean.Resp;
import com.java.core.utils.CookieUtils;
import com.java.gmall.auth.config.JwtProperties;
import com.java.gmall.auth.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangli
 * @since 2020/1/28 14:46
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	@Autowired
	private JwtProperties jwtProperties;

	@PostMapping("/accredit")
	public Resp<Object> accredit(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request,
	                             HttpServletResponse response) {
		String token = authService.accredit(username, password);
		if (StringUtils.isBlank(token)) {
			return Resp.fail("登录失败，用户名或密码错误");
		}
		// 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
		CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getExpire() * 60);
		return Resp.ok("登录成功");
	}
}
