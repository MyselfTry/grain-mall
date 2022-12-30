package com.java.gmall.cart.service;

import com.alibaba.fastjson.JSON;
import com.java.gmall.cart.constants.CartConstant;
import com.java.gmall.cart.feign.PmsFeign;
import com.java.gmall.cart.feign.SmsFeign;
import com.java.gmall.cart.interceptor.LoginInterceptor;
import com.java.gmall.cart.pojo.Cart;
import com.java.core.bean.UserInfo;
import com.java.gmall.pms.entity.SkuInfo;
import com.java.gmall.pms.entity.SkuSaleAttrValue;
import com.java.gmall.sms.vo.SaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangli
 * @since 2020/1/29 15:04
 */
@Service
public class CartService {
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private PmsFeign pmsFeign;
	@Autowired
	private SmsFeign smsFeign;

	/**
	 * 购物车信息存入redis
	 */
	public void addCart(Long skuId, Integer count) {
		String key = CartConstant.GMALL_CART;
		// 判断登录状态
		UserInfo userInfo = LoginInterceptor.userInfo();
		if (userInfo.getId() != null) {
			key += userInfo.getId();
		} else {
			key += userInfo.getUserKey();
		}

		// 查询用户购物车
		BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

		Cart cart = new Cart();
		// 查询商品购物车是否存在
		if (hashOps.hasKey(skuId.toString())) {
			// 购物车已存在该记录，更新数量
			String cartJson = hashOps.get(skuId.toString()).toString();
			cart = JSON.parseObject(cartJson, Cart.class);
			cart.setCount(cart.getCount() + count);
		} else {
			// 购物车不存在该记录，新增记录
			cart.setSkuId(skuId);
			cart.setCount(count);
			cart.setCheck(true);
			// 查询sku信息
			SkuInfo skuInfo = pmsFeign.querySkuBySkuId(skuId);
			cart.setDefaultImage(skuInfo.getSkuDefaultImg());
			cart.setPrice(skuInfo.getPrice());
			cart.setTitle(skuInfo.getSkuTitle());
			// 查询销售属性
			List<SkuSaleAttrValue> saleAttrValue = this.pmsFeign.querySkuSaleAttrValueBySkuId(skuId);
			cart.setSkuAttrValue(saleAttrValue);
			// 营销信息
			List<SaleVO> saleVOS = smsFeign.querySaleBySkuId(skuId);
			cart.setSaleVOS(saleVOS);

			// 将sku价格写入redis
			redisTemplate.opsForValue().set(CartConstant.GMALL_SKU + skuId, skuInfo.getPrice().toString());
		}
		// 将购物车记录写入redis
		hashOps.put(skuId.toString(), JSON.toJSONString(cart));
	}

	public List<Cart> queryCarts() {
		UserInfo userInfo = LoginInterceptor.userInfo();

		// 查询未登录购物车
		List<Cart> userKeyCarts = null;
		String userKey = CartConstant.GMALL_CART + userInfo.getUserKey();
		BoundHashOperations<String, Object, Object> userKeyOps = this.redisTemplate.boundHashOps(userKey);
		List<Object> cartJsonList = userKeyOps.values();
		if (!CollectionUtils.isEmpty(cartJsonList)) {
			userKeyCarts = cartJsonList.stream().map(cartJson -> {
				Cart cart = JSON.parseObject(cartJson.toString(), Cart.class);
				// 查询当前价格
				String currentPrice = redisTemplate.opsForValue().get(CartConstant.GMALL_SKU + cart.getSkuId());
				cart.setCurrentPrice(new BigDecimal(currentPrice));
				return cart;
			}).collect(Collectors.toList());
		}

		// 判断用户是否登录，未登录直接返回
		if (userInfo.getId() == null) {
			return userKeyCarts;
		}

		// 用户已登录，查询登录状态的购物车
		String key = CartConstant.GMALL_CART + userInfo.getId();
		BoundHashOperations<String, Object, Object> userIdOps = this.redisTemplate.boundHashOps(key); // 获取登录状态的购物车

		// 如果未登录状态的购物车不为空，需要合并
		if (!CollectionUtils.isEmpty(userKeyCarts)) {
			// 合并购物车
			userKeyCarts.forEach(userKeyCart -> {
				Long skuId = userKeyCart.getSkuId();
				Integer count = userKeyCart.getCount();
				if (userIdOps.hasKey(skuId.toString())) {
					// 购物车已存在该记录，更新数量
					String cartJson = userIdOps.get(skuId.toString()).toString();
					userKeyCart = JSON.parseObject(cartJson, Cart.class);
					userKeyCart.setCount(userKeyCart.getCount() + count);
				}
				// 购物车不存在该记录，新增记录
				userIdOps.put(skuId.toString(), JSON.toJSONString(userKeyCart));
			});
			// 合并完成后，删除未登录的购物车
			this.redisTemplate.delete(userKey);
		}

		// 返回登录状态的购物车
		List<Object> userCartJsonList = userIdOps.values();
		return userCartJsonList.stream().map(userCartJson -> {
			Cart cart = JSON.parseObject(userCartJson.toString(), Cart.class);
			// 查询当前价格
			String currentPrice = redisTemplate.opsForValue().get(CartConstant.GMALL_SKU + cart.getSkuId());
			cart.setCurrentPrice(new BigDecimal(currentPrice));
			return cart;
		}).collect(Collectors.toList());
	}

	public void updateCart(Cart cart) {
		// 获取登陆信息
		UserInfo userInfo = LoginInterceptor.userInfo();

		// 获取redis的key
		String key = CartConstant.GMALL_CART;
		if (userInfo.getId() == null) {
			key += userInfo.getUserKey();
		} else {
			key += userInfo.getId();
		}

		// 获取hash操作对象
		BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(key);
		String skuId = cart.getSkuId().toString();
		if (hashOperations.hasKey(skuId)) {
			// 获取购物车信息
			String cartJson = hashOperations.get(skuId).toString();
			Integer count = cart.getCount();
			cart = JSON.parseObject(cartJson, Cart.class);
			// 更新数量
			cart.setCount(count);
			// 写入购物车
			hashOperations.put(cart.getSkuId().toString(), JSON.toJSONString(cart));
		}
	}

	public void deleteCart(Long skuId) {
		// 获取登陆信息
		UserInfo userInfo = LoginInterceptor.userInfo();

		// 获取redis的key
		String key = CartConstant.GMALL_CART;
		if (userInfo.getId() == null) {
			key += userInfo.getUserKey();
		} else {
			key += userInfo.getId();
		}
		BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(key);
		hashOperations.delete(skuId.toString());
	}

	public List<Cart> queryCheckedCartsByUserId(Long userId) {
		BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(CartConstant.GMALL_CART + userId);
		List<Object> cartJsonList = hashOps.values();
		return cartJsonList.stream().map(cartJson->JSON.parseObject(cartJson.toString(),Cart.class)).filter(Cart::getCheck).collect(Collectors.toList());
	}
}
