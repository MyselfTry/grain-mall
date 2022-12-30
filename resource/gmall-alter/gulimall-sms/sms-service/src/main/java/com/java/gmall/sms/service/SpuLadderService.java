package com.java.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.gmall.sms.entity.SpuLadder;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;

/**
 * 商品阶梯价格
 *
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
public interface SpuLadderService extends IService<SpuLadder> {

    PageVo queryPage(QueryCondition params);
}

