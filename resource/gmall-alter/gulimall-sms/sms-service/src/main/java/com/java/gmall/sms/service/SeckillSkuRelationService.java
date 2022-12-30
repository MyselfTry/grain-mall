package com.java.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.sms.entity.SeckillSkuRelation;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 秒杀活动商品关联
 *
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelation> {

    PageVo queryPage(QueryCondition params);
}

