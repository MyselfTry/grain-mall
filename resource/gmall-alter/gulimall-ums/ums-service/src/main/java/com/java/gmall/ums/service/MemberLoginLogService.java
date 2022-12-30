package com.java.gmall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.ums.entity.MemberLoginLog;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 会员登录记录
 *
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
public interface MemberLoginLogService extends IService<MemberLoginLog> {

    PageVo queryPage(QueryCondition params);
}

