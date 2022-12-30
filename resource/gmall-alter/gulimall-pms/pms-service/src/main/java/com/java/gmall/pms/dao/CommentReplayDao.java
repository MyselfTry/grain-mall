package com.java.gmall.pms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.gmall.pms.entity.CommentReplay;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplay> {
	
}
