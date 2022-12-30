package com.java.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.sms.entity.CouponSpuCategoryRelation;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 优惠券分类关联
 *
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelation> {

    PageVo queryPage(QueryCondition params);
}

