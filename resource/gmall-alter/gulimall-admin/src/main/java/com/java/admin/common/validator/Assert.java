package com.java.admin.common.validator;

import cn.hutool.core.util.StrUtil;
import com.java.admin.common.exception.RRException;

/**
 * 数据校验
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StrUtil.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
