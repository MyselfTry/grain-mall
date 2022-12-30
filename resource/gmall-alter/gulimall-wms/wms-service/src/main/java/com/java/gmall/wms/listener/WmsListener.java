package com.java.gmall.wms.listener;

import com.alibaba.fastjson.JSON;
import com.java.gmall.wms.constant.WmsConstant;
import com.java.gmall.wms.dao.WareSkuDao;
import com.java.gmall.wms.service.WareSkuService;
import com.java.gmall.wms.vo.SkuLockVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author jiangli
 * @since 2020/1/31 18:28
 */
@Component
public class WmsListener {

	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private WareSkuDao wareSkuDao;

	/**
	 * 恢复锁定的商品库存信息
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "gmall.cart.unlock.queue", durable = "true"),
			exchange = @Exchange(value = "gmall-cart-exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
			key = {"cart.unlock"}
	))
	public void listenerCartDelete(String orderToken) {
		String jsonStr = redisTemplate.opsForValue().get(WmsConstant.WMS_LOCK + orderToken);

		if (StringUtils.isEmpty(jsonStr)) {
			return;
		}

		List<SkuLockVO> skuLockVOS = JSON.parseArray(jsonStr, SkuLockVO.class);
		skuLockVOS.forEach(skuLockVO -> this.wareSkuDao.unLockStore(skuLockVO.getWareId(),skuLockVO.getCount()));

		// 删除redis中锁定的库存信息
		redisTemplate.opsForValue().decrement(WmsConstant.WMS_LOCK + orderToken);
	}

	/**
	 * 支付成功后减库存
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "gmall-wms-minus-queue", durable = "true"),
			exchange = @Exchange(value = "GMALL-ORDER-EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
			key = {"stock.minus"}
	))
	public void minusStock(String orderToken){
		String jsonStr = redisTemplate.opsForValue().get(WmsConstant.WMS_LOCK + orderToken);
		List<SkuLockVO> skuLockVOS = JSON.parseArray(jsonStr, SkuLockVO.class);
		skuLockVOS.forEach(skuLockVO -> this.wareSkuDao.minusStock(skuLockVO.getWareId(),skuLockVO.getCount()));
	}
}
