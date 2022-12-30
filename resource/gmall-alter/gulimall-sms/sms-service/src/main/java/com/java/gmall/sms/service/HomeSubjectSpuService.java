package com.java.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.sms.entity.HomeSubjectSpu;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 专题商品
 *
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
public interface HomeSubjectSpuService extends IService<HomeSubjectSpu> {

    PageVo queryPage(QueryCondition params);
}

