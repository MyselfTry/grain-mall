package com.java.gmall.index.aspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.java.gmall.index.annotation.IndexCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangli
 * @since 2020/1/25 19:47
 */
@Component
@Aspect
public class IndexCacheAop {
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private RedissonClient redissonClient;

	@Around("@annotation(com.java.gmall.index.annotation.IndexCache)")
	public Object IndexCacheAround(ProceedingJoinPoint joinPoint) throws Throwable {
		// 获取注解属性
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		IndexCache indexCache = method.getAnnotation(IndexCache.class);
		// 获取缓存key的前缀
		String prefix = indexCache.prefix();

		//获取目标方法的参数列表
		Object[] args = joinPoint.getArgs();
		// 获取目标方法的返回值类型
		Class<?> returnType = method.getReturnType();

		String json;
		// 从缓存中查询
		String key = prefix + Arrays.asList(args).toString();
		json = redisTemplate.opsForValue().get(key);

		// 命中返回
		if (StrUtil.isNotEmpty(json)) {
			return JSON.parseObject(json, returnType);
		}

		// 没有命中,加分布式锁
		RLock lock = redissonClient.getLock("lock" + Arrays.asList(args).toString());
		lock.lock();

		// 再次查询缓存,命中返回
		json = redisTemplate.opsForValue().get(key);

		// 命中返回
		if (StrUtil.isNotEmpty(json)) {
			lock.unlock();
			return JSON.parseObject(json, returnType);
		}

		// 没有则执行目标方法
		Object result = joinPoint.proceed(args);

		// 放入缓存,释放分布式锁
		int timeout = indexCache.timeout();
		int random = indexCache.random();
		redisTemplate.opsForValue().set(key, JSON.toJSONString(result), timeout + new Random().nextInt(random), TimeUnit.MINUTES);

		lock.unlock();

		return result;

	}
}
