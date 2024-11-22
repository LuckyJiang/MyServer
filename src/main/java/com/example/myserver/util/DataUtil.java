package com.example.myserver.util;


import java.util.regex.Pattern;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public class DataUtil {

    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    private boolean isNumeric(final String str) {
        final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches() && !str.contains(" ");
    }
}
