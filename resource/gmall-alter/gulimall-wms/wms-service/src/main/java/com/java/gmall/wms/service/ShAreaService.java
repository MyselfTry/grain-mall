package com.java.gmall.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.entity.ShArea;

/**
 * 全国省市区信息
 *
 * @author jiangli
 * @since  2020-01-11 15:49:45
 */
public interface ShAreaService extends IService<ShArea> {

    PageVo queryPage(QueryCondition params);
}

