package com.example.myserver.excel.constant;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public enum QuestionDifficultyEnum {
    SIMPLE("简单"),
    MEDIUM("中等"),
    COMPLEX("复杂");
    
    private static final Map<String, QuestionDifficultyEnum> CACHE = new ConcurrentHashMap<>();
    
    static {
        for (QuestionDifficultyEnum questionTypeEnum : values()) {
            CACHE.put(questionTypeEnum.getTypeString(), questionTypeEnum);
        }
    }
    
    private String type;
    
    QuestionDifficultyEnum(final String type) {
        this.type = type;
    }

    public String getTypeString() {
        return type;
    }
    
    public static boolean contains(final String type) {
        return CACHE.containsKey(type);
    }
    
}
