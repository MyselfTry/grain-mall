package com.java.gmall.cart.listener;

import com.java.core.bean.Resp;
import com.java.gmall.cart.constants.CartConstant;
import com.java.gmall.cart.feign.PmsFeign;
import com.java.gmall.pms.entity.SkuInfo;
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
 * @since 2020/1/29 16:44
 */
@Component
public class CartListener {
	@Autowired
	private PmsFeign pmsFeign;
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 更新redis中sku的价格
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "gmall.cart.update.queue", durable = "true"),
			exchange = @Exchange(
					value = "gmall.item.exchange", /*同pms中的exchange名*/
					ignoreDeclarationExceptions = "true",
					type = ExchangeTypes.TOPIC),
			key = {"item.update"}
	))
	public void listenerSkuPriceUpdate(Long spuId) {
		Resp<List<SkuInfo>> listResp = pmsFeign.querySkuBySpuId(spuId);
		List<SkuInfo> skuInfos = listResp.getData();
		skuInfos.forEach(skuInfo -> {
			// 更新redis中的sku的价格
			redisTemplate.opsForValue().set(CartConstant.GMALL_SKU + skuInfo.getSkuId(), skuInfo.getPrice().toString());
		});
	}

	/**
	 * 删除redis中的购物车
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "gmall.cart.delete.queue", durable = "true"),
			exchange = @Exchange(
					value = "gmall-cart-exchange",
					ignoreDeclarationExceptions = "true",
					type = ExchangeTypes.TOPIC),
			key = {"cart.delete"}
	))
	public void listenerCartDelete(Map<String, Object> msg) {
		String userId = msg.get("userId").toString();
		List<String> skuIds = (List<String>) msg.get("skuIds");
		BoundHashOperations<String, Object, Object> ops = this.redisTemplate.boundHashOps(CartConstant.GMALL_CART + userId);
		ops.delete(skuIds.toArray());

		// 类型转换
		// String[] ids = skuIds.toArray(new String[skuIds.size()]);
	}
}
