package com.java.gmall.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author jiangli
 * @since 2020/1/28 16:55
 * 过滤器工厂
 * 规范:工厂类要以****GatewayFilterFactory为结尾，前面的名称为配置文件的属性，所以配置文件中就用Auth作为属性
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
	@Autowired
	private AuthGatewayFilter authGatewayFilter;

	@Override
	public GatewayFilter apply(Object config) {
		return authGatewayFilter;
	}
}
