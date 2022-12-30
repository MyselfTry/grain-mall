package com.java.gmall.cart.controller;

import com.java.core.bean.Resp;
import com.java.gmall.cart.pojo.Cart;
import com.java.gmall.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/29 13:04
 */
@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	/**
	 * 根据用户id查询已选择的购物车信息
	 */
	@GetMapping("{userId}")
	public ResponseEntity<List<Cart>> queryCheckedCartsByUserId(@PathVariable("userId") Long userId) {
		List<Cart> carts = cartService.queryCheckedCartsByUserId(userId);
		return ResponseEntity.ok(carts);
	}

	/**
	 * 添加购物车
	 */
	@PostMapping
	public Resp<String> addCart(@RequestParam("skuId") Long skuId, @RequestParam("count") Integer count) {
		cartService.addCart(skuId, count);

		return Resp.ok("添加购物车成功");
	}

	/**
	 * 查询购物车
	 */
	@GetMapping
	public ResponseEntity<List<Cart>> queryCarts() {
		List<Cart> carts = this.cartService.queryCarts();

		return ResponseEntity.ok(carts);
	}

	/**
	 * 更新购物车
	 */
	@PostMapping("update")
	public Resp<Object> updateCart(@RequestBody Cart cart) {
		this.cartService.updateCart(cart);

		return Resp.ok(null);
	}

	/**
	 * 删除购物车
	 */
	@PostMapping("{skuId}")
	public Resp<Object> deleteCart(@PathVariable("skuId")Long skuId){
		this.cartService.deleteCart(skuId);

		return Resp.ok(null);
	}

}
