package com.java.core.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangli
 * @since 2020/1/27 18:40
 */
@Configuration
public class ThreadPoolConfig {

	@Bean
	public ThreadPoolExecutor threadPoolExecutor() {
		return new ThreadPoolExecutor(50,500,30, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10000));
	}
}
