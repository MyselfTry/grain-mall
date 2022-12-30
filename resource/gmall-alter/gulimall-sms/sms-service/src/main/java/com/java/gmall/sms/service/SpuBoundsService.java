package com.java.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.sms.entity.SpuBounds;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 商品spu积分设置
 *
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
public interface SpuBoundsService extends IService<SpuBounds> {

    PageVo queryPage(QueryCondition params);
}

