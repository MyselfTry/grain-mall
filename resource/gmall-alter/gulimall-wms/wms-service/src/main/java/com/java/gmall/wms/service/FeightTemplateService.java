package com.java.gmall.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.entity.FeightTemplate;

/**
 * 运费模板
 *
 * @author jiangli
 * @since  2020-01-11 15:49:45
 */
public interface FeightTemplateService extends IService<FeightTemplate> {

    PageVo queryPage(QueryCondition params);
}

