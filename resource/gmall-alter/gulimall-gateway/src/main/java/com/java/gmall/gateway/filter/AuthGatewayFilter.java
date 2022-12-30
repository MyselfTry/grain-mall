package com.java.gmall.gateway.filter;

import com.java.gmall.gateway.config.JwtProperties;
import com.java.gmall.gateway.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author jiangli
 * @since 2020/1/28 16:53
 * 过滤器
 */
@Component
public class AuthGatewayFilter implements GatewayFilter {
	@Autowired
	private JwtProperties jwtProperties;

	/**
	 - 获取cookie中的token
	 - 通过JWT对token进行校验
	 - 通过：则放行；不通过：则响应认证未通过
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 获取request和response，注意：不是HttpServletRequest及HttpServletResponse
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		// 获取所有cookie
		MultiValueMap<String, HttpCookie> cookies = request.getCookies();
		// 如果cookies为空或者不包含指定的token，则相应认证未通过
		if (CollectionUtils.isEmpty(cookies) || !cookies.containsKey(this.jwtProperties.getCookieName())) {
			// 响应未认证！
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			// 结束请求
			return response.setComplete();
		}
		// 获取cookie
		HttpCookie cookie = cookies.getFirst(this.jwtProperties.getCookieName());
		try {
			// 校验cookie
			JwtUtils.getInfoFromToken(cookie.getValue(), this.jwtProperties.getPublicKey());
		} catch (Exception e) {
			e.printStackTrace();
			// 校验失败，响应未认证
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}

		// 认证通过放行
		return chain.filter(exchange);
	}
}
