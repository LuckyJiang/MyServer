package com.example.myserver.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Data
@ToString
public class QuestionExcel {


    @ExcelProperty(value = "题库编码")
    private String code;
    
    @ExcelProperty(value = "难度")
    private String difficulty;
    
    @ExcelProperty(value = "题型")
    private String type;

    @ExcelProperty(value = "分数")
    private String score;

    @ExcelProperty(value = "题目内容")
    private String content;

    @ExcelProperty(value = "参考答案")
    private String answer;

    @ExcelProperty(value = "答案解析")
    private String explanation;

    private List<String> options = new ArrayList<>();
    
}
