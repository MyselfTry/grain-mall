package com.java.gmall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.ums.entity.MemberCollectSubject;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 会员收藏的专题活动
 *
 * @author jiangli
 * @since  2020-01-27 19:50:04
 */
public interface MemberCollectSubjectService extends IService<MemberCollectSubject> {

    PageVo queryPage(QueryCondition params);
}

