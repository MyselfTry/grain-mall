package com.java.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.java.admin.common.util.PageUtils;
import com.java.admin.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
