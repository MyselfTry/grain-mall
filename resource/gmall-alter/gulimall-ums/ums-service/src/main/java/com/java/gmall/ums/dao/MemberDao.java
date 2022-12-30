package com.java.gmall.ums.dao;

import com.java.gmall.ums.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
@Mapper
public interface MemberDao extends BaseMapper<Member> {
	
}
