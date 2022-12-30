package com.java.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.sms.entity.SeckillPromotion;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 秒杀活动
 *
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
public interface SeckillPromotionService extends IService<SeckillPromotion> {

    PageVo queryPage(QueryCondition params);
}

