package com.java.gmall.pms.service.impl;

import com.java.gmall.pms.dao.CategoryDao;
import com.java.gmall.pms.entity.Category;
import com.java.gmall.pms.service.CategoryService;
import com.java.gmall.pms.vo.CategoryVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.bean.PageVo;
import com.java.core.bean.Query;
import com.java.core.bean.QueryCondition;

import java.util.List;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<Category> page = this.page(
                new Query<Category>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageVo(page);
    }

	@Override
	public List<CategoryVO> queryCategoryVO(Long pid) {
		return this.baseMapper.queryCategoryVO(pid);
	}

}