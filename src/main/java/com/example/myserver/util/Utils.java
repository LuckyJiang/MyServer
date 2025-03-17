package com.example.myserver.util;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public class Utils {
    
    public void map(final Map<String, Object> maps){
        /**
         * 不需要再判断为空的情况
         */
        final Object value = maps.getOrDefault("id", Maps.newHashMap());
    }
}
