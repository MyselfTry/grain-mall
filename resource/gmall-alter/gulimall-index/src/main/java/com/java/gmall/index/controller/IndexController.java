package com.java.gmall.index.controller;

import com.java.core.bean.Resp;
import com.java.gmall.index.service.IndexService;
import com.java.gmall.pms.entity.Category;
import com.java.gmall.pms.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/17 22:17
 */
@RestController
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private IndexService indexService;

	@GetMapping("/cates")
	public Resp<List<Category>> queryLevel1Categories() {
		List<Category> categories = indexService.queryLevel1Categories();
		return Resp.ok(categories);
	}

	@GetMapping("/cates/{pid}")
	public Resp<List<CategoryVO>> queryCategoryVO(@PathVariable("pid")Long pid){
		return indexService.queryCategoryVO(pid);
	}

	@GetMapping("/main")
	public String testMain() throws InterruptedException {
		return indexService.testMain();
	}

	@GetMapping("/sub")
	public String testSub() {
		return indexService.testSub();
	}


}
