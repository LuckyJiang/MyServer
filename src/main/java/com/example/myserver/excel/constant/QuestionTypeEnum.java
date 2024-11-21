package com.example.myserver.excel.constant;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public enum QuestionTypeEnum {
    SINGLE_CHOICE("单选题"),
    MULTIPLE_CHOICE("多选题"),
    JUDGE("判断题"),
    FILL_BLANK("填空题"),
    SHORT_ANSWER("简答题");

    private static final Map<String, QuestionTypeEnum> CACHE = new ConcurrentHashMap<>();

    static {
        for (QuestionTypeEnum questionTypeEnum : values()) {
            CACHE.put(questionTypeEnum.getTypeString(), questionTypeEnum);
        }
    }
    
    private String type;
    
    QuestionTypeEnum(final String type) {
        this.type = type;
    }

    public String getTypeString() {
        return type;
    }
    
    public static boolean contains(final String type) {
        return CACHE.containsKey(type);
    }
    
}
