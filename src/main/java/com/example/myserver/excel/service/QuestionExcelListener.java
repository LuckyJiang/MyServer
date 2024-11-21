package com.example.myserver.excel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.util.StringUtils;
import com.example.myserver.excel.constant.QuestionDifficultyEnum;
import com.example.myserver.excel.constant.QuestionTypeEnum;
import com.example.myserver.excel.model.QuestionExcel;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Data
public class QuestionExcelListener extends AnalysisEventListener<Map<Integer, String>> {
    
    private List<QuestionExcel> list = Lists.newLinkedList();

    private Map<Integer, String> headMap;

    private List<CellExtra> cellExtraList = new ArrayList<>();

    private List<Map<Integer, String>> dataList = new ArrayList<>();

    @Override
    public void invokeHeadMap(final Map<Integer, String> map, final AnalysisContext context) {
        this.headMap = map;
    }

    @Override
    public void invoke(final Map<Integer, String> data, final AnalysisContext analysisContext) {
        dataList.add(data);
    }


    @Override
    public void doAfterAllAnalysed(final AnalysisContext context) {
        // 所有数据解析完成后的操作，可以在这里处理 dataList
        for (Map<Integer, String> row : dataList) {
            final QuestionExcel questionExcel = new QuestionExcel();
            String lastHeadName = null;
            for (Integer key : headMap.keySet())  {
                String headName = headMap.get(key);
                if (headName == null && isMergeCell(key)) {
                    headName = lastHeadName;
                } 
                if (!StringUtils.isEmpty(headName)) {
                    lastHeadName = headName;
                } else {
                    continue;
                }
                extracted(row, key, headName, questionExcel);
            }
            validate(questionExcel);
            list.add(questionExcel);
        }
    }

    private static void extracted(final Map<Integer, String> row, 
                                  final Integer key, 
                                  final String headName, 
                                  final QuestionExcel questionExcel) {
        final String v = row.get(key);
        switch (headName) {
            case "题库编码":
                questionExcel.setCode(v);
                break;
            case "题目内容":
                questionExcel.setContent(v);
                break;
            case "参考答案":
                questionExcel.setAnswer(v);
                break;
            case "答案解析":
                questionExcel.setExplanation(v);
                break;
            case "难度":
                questionExcel.setDifficulty(v);
                break;
            case "题型":
                questionExcel.setType(v);
                break;
            case "分数":
                questionExcel.setScore(v);
                break;
            case "选项":
                if (!StringUtils.isEmpty(v)) {
                    questionExcel.getOptions().add(v);
                }
                break;
            default:
                break;
        }
    }

    private boolean isMergeCell(final Integer columnIndex) {
        for (CellExtra cellExtra : cellExtraList) {
            final Integer firstColumnIndex = cellExtra.getFirstColumnIndex();
            final Integer lastColumnIndex = cellExtra.getLastColumnIndex();
            if (columnIndex >= firstColumnIndex && columnIndex <= lastColumnIndex) {
                return true;
            }
        }
        return false;
    }
    
    
    

    @Override
    public void extra(final CellExtra extra, final AnalysisContext context) {
        final CellExtraTypeEnum type = extra.getType();
        switch (type) {
            case MERGE:
                final Integer firstColumnIndex = extra.getFirstColumnIndex();
                final Integer lastColumnIndex = extra.getLastColumnIndex();
                if (lastColumnIndex > firstColumnIndex) {
                    cellExtraList.add(extra);
                }
                break;
            default:
                break;
        }
    }

    private static void validate(final String value, final String errorMsg) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    private static void validate(final QuestionExcel data) {
        validate(data.getCode(), "题库编码为空");

        validate(data.getDifficulty(), "试题难度不能为空");

        validate(data.getContent(), "试题内容不能为空");

        validate(data.getAnswer(), "试题参考答案不能为空");

        validate(data.getScore(), "试题分值不能为空");

        validate(data.getType(), "试题类型不能为空");

        if (!QuestionDifficultyEnum.contains(data.getDifficulty())) {
            throw new IllegalArgumentException("试题难度不存在：" + data.getDifficulty() + ", 可选值：简单、中等、复杂");
        }

        if (QuestionTypeEnum.SINGLE_CHOICE.getTypeString().equals(data.getType()) 
                || data.getType().equals(QuestionTypeEnum.MULTIPLE_CHOICE.getTypeString())) {
            data.setAnswer(data.getAnswer().toUpperCase());
        }

        if (!QuestionTypeEnum.contains(data.getType())) {
            throw new IllegalArgumentException("试题类型不存在：" + data.getType());
        }
    }
    
    public List<QuestionExcel> getData() {
        return this.list;
    }
    
    
}
