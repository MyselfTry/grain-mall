package com.java.gmall.cart.interceptor;

import com.java.core.utils.CookieUtils;
import com.java.core.utils.JwtUtils;
import com.java.gmall.cart.config.JwtProperties;
import com.java.core.bean.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * 统一获取登录状态
 *
 * @author jiangli
 * @since 2020/1/29 12:58
 * 1.定义login拦截器
 * 2.注册自定义拦截器
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private JwtProperties jwtProperties;

	private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserInfo userInfo = new UserInfo();
		// 获取cookie中的token信息(jwt)和userKey信息
		String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
		String userKey = CookieUtils.getCookieValue(request, jwtProperties.getUserKey());
		// 没有userKey则生成放入cookie中
		if (StringUtils.isEmpty(userKey)) {
			userKey = UUID.randomUUID().toString();
			CookieUtils.setCookie(request, response, jwtProperties.getUserKey(), userKey, Integer.MAX_VALUE);
		}

		userInfo.setUserKey(userKey);
		// 解析token
		if (StringUtils.isNotEmpty(token)) {
			Map<String, Object> info = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
			userInfo.setId(new Long(info.get("id").toString()));
		}

		THREAD_LOCAL.set(userInfo);
		return super.preHandle(request, response, handler);
	}

	public static UserInfo userInfo() {
		return THREAD_LOCAL.get();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 必须手动清除ThreadLocal中线程变量,因为使用的是tomcat的线程池
		THREAD_LOCAL.remove();
	}
}
