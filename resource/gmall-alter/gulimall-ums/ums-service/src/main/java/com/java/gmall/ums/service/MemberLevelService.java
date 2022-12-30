package com.java.gmall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.ums.entity.MemberLevel;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 会员等级
 *
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
public interface MemberLevelService extends IService<MemberLevel> {

    PageVo queryPage(QueryCondition params);
}

