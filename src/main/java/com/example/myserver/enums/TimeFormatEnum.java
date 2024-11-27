package com.example.myserver.enums;


/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public enum TimeFormatEnum {
    DATE_LINE_TIME_COLON("yyyy-MM-dd HH:mm:ss"),
    DATE_POINT("yyyy.MM.dd"),
    DATE_LINE("yyyy-MM-dd");
    
    private String format;

    TimeFormatEnum(final String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
