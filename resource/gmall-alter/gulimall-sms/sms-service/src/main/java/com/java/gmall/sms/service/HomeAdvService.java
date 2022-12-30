package com.java.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.sms.entity.HomeAdv;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 首页轮播广告
 *
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
public interface HomeAdvService extends IService<HomeAdv> {

    PageVo queryPage(QueryCondition params);
}

