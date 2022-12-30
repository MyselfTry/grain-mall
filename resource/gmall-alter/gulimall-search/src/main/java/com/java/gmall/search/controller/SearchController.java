package com.java.gmall.search.controller;

import com.java.core.bean.Resp;
import com.java.gmall.search.service.SearchService;
import com.java.gmall.search.vo.SearchParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author jiangli
 * @since 2020/1/14 20:36
 */
@RestController
@RequestMapping("search")
public class SearchController {
	@Autowired
	private SearchService searchService;

	@GetMapping
	public Resp search(SearchParamVO searchParamVO) throws IOException {
		searchService.search(searchParamVO);
		return Resp.ok(null);
	}
}
