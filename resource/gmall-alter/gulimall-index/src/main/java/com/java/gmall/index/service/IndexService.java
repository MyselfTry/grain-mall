package com.java.gmall.index.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.java.core.bean.Resp;
import com.java.gmall.index.annotation.IndexCache;
import com.java.gmall.index.feign.PmsFeign;
import com.java.gmall.pms.entity.Category;
import com.java.gmall.pms.vo.CategoryVO;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangli
 * @since 2020/1/17 22:19
 */
@Service
public class IndexService {
	@Autowired
	private PmsFeign pmsFeign;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private RedissonClient redissonClient;

	public static final String INDEX_CATEGORY = "index:category:";

	public List<Category> queryLevel1Categories() {
		Resp<List<Category>> listResp = pmsFeign.queryCategoryByPidOrLevel(0L, 1);
		return listResp.getData();
	}

	@IndexCache(prefix = INDEX_CATEGORY, timeout = 7200, random = 100)
	public Resp<List<CategoryVO>> queryCategoryVO(@PathVariable("pid") Long pid) {
		List<CategoryVO> categoryVOS;
		// 判断缓存中有没有
/*		String json = redisTemplate.opsForValue().get(INDEX_CATEGORY + pid);
		if (StrUtil.isNotEmpty(json)) {
			categoryVOS = JSON.parseArray(json, CategoryVO.class);
			return Resp.ok(categoryVOS);
		}*/

		// 加分布式锁
	/*	RLock lock = redissonClient.getLock("lock" + pid);
		lock.lock();*/

		// 加锁之后再判断一次redis中有没有
/*		String json1 = redisTemplate.opsForValue().get(INDEX_CATEGORY + pid);
		if (StrUtil.isNotEmpty(json1)) {
			categoryVOS = JSON.parseArray(json, CategoryVO.class);
		} else {*/
			categoryVOS = pmsFeign.queryCategoryVO(pid);
			// 存入缓存
	/*		redisTemplate.opsForValue().set(INDEX_CATEGORY + pid, JSONUtil.toJsonStr(categoryVOS), 7 + new Random().nextInt(5), TimeUnit.DAYS);
		}*/

		// 释放锁
		/*lock.unlock();*/
		return Resp.ok(categoryVOS);
	}

	public void testLock() {
		RLock lock = this.redissonClient.getLock("lock"); // 只要锁的名称相同就是同一把锁
		lock.lock(); // 加锁

		// 执行业务逻辑代码
		// 查询redis中的num值
		String value = this.redisTemplate.opsForValue().get("num");
		// 没有该值return
		if (StrUtil.isEmpty(value)) {
			return;
		}
		// 有值就转成成int
		int num = Integer.parseInt(value);
		// 把redis中的num值+1
		this.redisTemplate.opsForValue().set("num", String.valueOf(++num));

		// 释放锁资源
		lock.unlock(); // 解锁
	}

	public String testMain() throws InterruptedException {
		RCountDownLatch latch = this.redissonClient.getCountDownLatch("latch");
		latch.trySetCount(5L);

		latch.await();
		return "主业务开始执行";

	}

	public String testSub() {
		RCountDownLatch latch = this.redissonClient.getCountDownLatch("latch");
		latch.countDown();

		return "分支业务执行了一次";
	}
}
