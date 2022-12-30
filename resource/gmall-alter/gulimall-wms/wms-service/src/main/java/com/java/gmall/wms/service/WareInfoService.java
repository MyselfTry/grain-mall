package com.java.gmall.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.gmall.wms.entity.WareInfo;

/**
 * 仓库信息
 *
 * @author jiangli
 * @since  2020-01-11 15:49:45
 */
public interface WareInfoService extends IService<WareInfo> {

    PageVo queryPage(QueryCondition params);
}

