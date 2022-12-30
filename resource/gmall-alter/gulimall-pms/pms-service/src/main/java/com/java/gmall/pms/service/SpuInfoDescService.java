package com.java.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.pms.entity.SpuInfoDesc;


/**
 * spu信息介绍
 *
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
public interface SpuInfoDescService extends IService<SpuInfoDesc> {

    PageVo queryPage(QueryCondition params);
}

