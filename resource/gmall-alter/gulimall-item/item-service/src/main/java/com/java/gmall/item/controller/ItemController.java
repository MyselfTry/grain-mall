package com.java.gmall.item.controller;

import com.java.core.bean.Resp;
import com.java.gmall.item.service.ItemService;
import com.java.gmall.item.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangli
 * @since 2020/1/26 13:28
 */
@RestController
@RequestMapping("item")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@GetMapping("{skuId}")
	public Resp<ItemVO> queryItem(@PathVariable("skuId") Long skuId) {
		ItemVO itemVO = itemService.queryItem(skuId);
		return Resp.ok(itemVO);
	}

}
