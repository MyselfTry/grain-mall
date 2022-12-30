package com.java.gmall.index.annotation;

import org.springframework.transaction.TransactionDefinition;

import java.lang.annotation.*;

/**
 * @author jiangli
 * @since 2020/1/25 19:40
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IndexCache {

	// 缓存中key的前缀
	String prefix() default "";

	// 过期时间 单位:分
	int timeout() default 5;

	// 随机时间 单位:分
	int random() default 5;

}
