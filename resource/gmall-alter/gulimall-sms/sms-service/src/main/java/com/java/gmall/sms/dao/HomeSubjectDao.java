package com.java.gmall.sms.dao;

import com.java.gmall.sms.entity.HomeSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Mapper
public interface HomeSubjectDao extends BaseMapper<HomeSubject> {
	
}
