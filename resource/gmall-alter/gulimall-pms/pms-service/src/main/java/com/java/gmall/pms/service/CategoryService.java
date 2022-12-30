package com.java.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.entity.Category;
import com.java.gmall.pms.vo.CategoryVO;

import java.util.List;


/**
 * 商品三级分类
 *
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
public interface CategoryService extends IService<Category> {

    PageVo queryPage(QueryCondition params);

	List<CategoryVO> queryCategoryVO(Long pid);
}

