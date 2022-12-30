package com.java.gmall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.ums.entity.Member;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 会员
 *
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
public interface MemberService extends IService<Member> {

    PageVo queryPage(QueryCondition params);

	Boolean checkData(String data, Integer type);

	void register(Member memberEntity, String code);

	Member queryUser(String username, String password);
}

