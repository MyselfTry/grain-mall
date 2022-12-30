package com.java.gmall.wms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.constant.WmsConstant;
import com.java.gmall.wms.dao.WareSkuDao;
import com.java.gmall.wms.entity.WareSku;
import com.java.gmall.wms.service.WareSkuService;
import com.java.gmall.wms.vo.SkuLockVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSku> implements WareSkuService {
	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	public PageVo queryPage(QueryCondition params) {
		IPage<WareSku> page = this.page(
				new Query<WareSku>().getPage(params),
				new QueryWrapper<>()
		);

		return new PageVo(page);
	}

	@Override
	public String checkAndLockStore(List<SkuLockVO> skuLockVOS) {
		if (CollectionUtils.isEmpty(skuLockVOS)) {
			return "没有选中商品";
		}

		// 检验并锁定库存
		skuLockVOS.forEach(this::lockStore);

		// 查出库存不够的商品进行提示
		List<SkuLockVO> unLockSkus = skuLockVOS.stream().filter(skuLockVO -> !skuLockVO.getLocked()).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(unLockSkus)) {
			// 恢复已锁定的库存
			List<SkuLockVO> lockSkus = skuLockVOS.stream().filter(SkuLockVO::getLocked).collect(Collectors.toList());
			lockSkus.forEach(skuLockVO -> {
				this.baseMapper.unLockStore(skuLockVO.getWareId(), skuLockVO.getCount());
			});

			List<Long> ids = unLockSkus.stream().map(SkuLockVO::getSkuId).collect(Collectors.toList());
			return "商品" + ids.toString() + "库存不足,请重新购买";
		}

		// 将锁定的商品信息保存在redis
		String orderToken = skuLockVOS.get(0).getOrderToken();
		redisTemplate.opsForValue().set(WmsConstant.WMS_LOCK + orderToken, JSON.toJSONString(skuLockVOS));

		// 锁定库存后,发送延时消息,定时释放库存
		amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE", "store.create", orderToken);

		return null;
	}

	private void lockStore(SkuLockVO skuLockVO) {
		Integer count = skuLockVO.getCount();
		RLock lock = redissonClient.getLock("stock:" + skuLockVO.getSkuId());
		lock.lock();
		// 查询剩余库存够不够
		List<WareSku> wareSkus = this.baseMapper.checkStore(skuLockVO.getSkuId(), count);
		if (!CollectionUtils.isEmpty(wareSkus)) {
			// 锁定库存信息
			this.baseMapper.lockStore(wareSkus.get(0).getId(), count);
			skuLockVO.setLocked(true);
			skuLockVO.setWareId(wareSkus.get(0).getId());
		} else {
			skuLockVO.setLocked(false);
		}

		lock.unlock();
	}

}