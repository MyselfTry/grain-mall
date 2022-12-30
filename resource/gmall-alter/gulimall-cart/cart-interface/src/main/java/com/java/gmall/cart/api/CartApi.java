package com.java.gmall.cart.api;

import com.java.gmall.cart.pojo.Cart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/30 11:42
 */
public interface CartApi {

	/**
	 * 根据用户id查询已选择的购物车信息
	 */
	@GetMapping("cart/{userId}")
	List<Cart> queryCheckedCartsByUserId(@PathVariable("userId") Long userId);
}
