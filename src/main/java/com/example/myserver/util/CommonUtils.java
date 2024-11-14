package com.example.myserver.util;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public final class CommonUtils {
    
    private CommonUtils() {
    }
    
    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static Date format(final String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return parser.parse(time);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间格式异常");
        }
    }

}
