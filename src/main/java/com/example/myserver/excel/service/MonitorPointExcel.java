package com.example.myserver.excel.service;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.util.StringUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

@Data
@Slf4j
public class MonitorPointExcel<T> extends AnalysisEventListener<Map<Integer, String>> {

    private static Map<String, String[]> fieldHeadMap = new HashMap<>();

    private static String sheetName;

    private static List<String> errorMsgList = Lists.newArrayList();
    
    private final Class<T> clazz;

    private final List<Map<String, String>> result = Lists.newArrayList();

    private final Integer maxHeadLevel;

    private final Map<String, Integer> fieldIndexMap;

    private final List<Map<Integer, String>> lastRowMap;

    private final List<CellExtra> cellExtraList;
    
    private final Integer headerRowNum;

    public MonitorPointExcel(final Class<T> clazz,
                             final Integer maxHeadLevel,
                             final List<CellExtra> cellExtraList,
                             final Integer headerRowNum,
                             final String sheetName) {
        this.clazz = clazz;
        this.maxHeadLevel = maxHeadLevel;
        this.headerRowNum = headerRowNum;
        this.cellExtraList = cellExtraList;
        final Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            final ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
            if (Objects.nonNull(annotation)) {
                final String[] value = annotation.value();
                this.fieldHeadMap.put(declaredField.getName(), value);
            }
        }
        this.sheetName = sheetName;
        this.fieldIndexMap = new HashMap<>();
        this.lastRowMap = new ArrayList<>();
    }

    //赋值合并单元格内容属性
    private String checkIsMerge(final Integer rowIndex, 
                                final Integer columnIndex, 
                                final String text) {
        for (CellExtra item : cellExtraList) {
            if ((rowIndex >= item.getFirstRowIndex() && rowIndex <= item.getLastRowIndex())
                    && (columnIndex >= item.getFirstColumnIndex() && columnIndex <= item.getLastColumnIndex())) {
                if (!StringUtils.isEmpty(text)) {
                    item.setText(text);
                }
                return item.getText();
            }
        }
        return text;
    }
    
    @Override
    public void invokeHeadMap(final Map<Integer, String> headMap, final AnalysisContext context) {
        final Integer currentRowIndex = context.readRowHolder().getRowIndex();
        if (currentRowIndex > 0) {
            final HashMap<Integer, String> newRowData = new HashMap<>();
            //遍历当前行，通过和合并单元格对比替换内容
            headMap.forEach((key, item) -> {
                newRowData.put(key, checkIsMerge(context.readRowHolder().getRowIndex(), key, item));
            });
            //遍历字段表头关系，设置字段-列索引关系
            fieldHeadMap.forEach((key, item) -> {
                if (item.length >= currentRowIndex) {
                    updateFieldIndexMap(key, item, newRowData, currentRowIndex);
                }

            });
            lastRowMap.add(newRowData);
        }
        
    }

    private void updateFieldIndexMap(final String key, 
                                     final String[] item, 
                                     final HashMap<Integer, String> newRowData, 
                                     final Integer currentRowIndex) {
        newRowData.forEach((index, header) -> {
            if (item[currentRowIndex - 1].equals(header)) {
                update(key, item, currentRowIndex, index);
            }
        });
    }

    private void update(final String key,
                        final String[] item,
                        final Integer currentRowIndex, 
                        final Integer index) {
        if (currentRowIndex == 1) {
            fieldIndexMap.put(key, index);
        } else {
            final boolean flag = isFlag(item, currentRowIndex, index);
            if (flag) {
                fieldIndexMap.put(key, index);
            }
        }
    }

    private boolean isFlag(final String[] item, final Integer currentRowIndex, final Integer index) {
        boolean flag = false;
        for (int i = currentRowIndex - 1; i > 0; i--) {
            if (lastRowMap.get(i - 1).get(index).equals(item[i - 1])) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    //数据读取监听
    @Override
    public void invoke(final Map<Integer, String> integerStringMap, final AnalysisContext context) {
        //处理正式数据
        final Integer currentRowIndex = context.readRowHolder().getRowIndex();
        if (fieldIndexMap.size() > 0) {
            final Map<String, String> map = new HashMap<>();
            fieldIndexMap.forEach((key, index) -> {
                final String value = Objects.isNull(integerStringMap.get(index)) 
                        ? "" : String.valueOf(integerStringMap.get(index));
                map.put(key, value);
            });
            result.add(mapToEntity(map, currentRowIndex));
        }

    }
    
    private <T> T mapToEntity(final Map<String, String> map, final Integer currentRowIndex) {
        T object = null;
        try {
            final Constructor<T> constructor = (Constructor<T>) clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            object = constructor.newInstance();
            final Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                field.setAccessible(true);
                final String value = Objects.isNull(map.get(field.getName())) ? "" : map.get(field.getName());
                if (validateField(field, value, currentRowIndex)) {
                    field.set(object, value);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create or set object", e);
        }
        return object;
    }

    private boolean validateField(final Field field, final String value, final Integer rowIndex) {
        if ("tagTime".equals(field.getName()) 
                || "confirmTime".equals(field.getName())
                || "inspectionTime".equals(field.getName())) {
            return validateTimeField(field, value, rowIndex);
        }
        return true;
    }

    private static boolean validateTimeField(final Field field, final String value, final Integer rowIndex) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return true;
        }
        final String regex = "^\\d{4}\\.(0?[1-9]|1[0-2])\\.(0?[1-9]|[12][0-9]|3[01])$";
        final boolean matches = value.matches(regex);
        if (!matches) {
            addErrorMsg(field, value, rowIndex);
        }
        return matches;
    }

    private static void addErrorMsg(final Field field, final String value, final Integer rowIndex) {
        final String[] strings = fieldHeadMap.get(field.getName());
        /*errorMsgList.add("Sheet页：" + sheetName + "第" + (rowIndex + 1) + "行" 
                + strings[strings.length - 1] + "字段格式不正确:" + value);*/
        log.info("Sheet页：{},第{}行{}字段格式不正确:{}", sheetName, rowIndex + 1, strings[strings.length - 1], value);
    }

    @Override
    public void doAfterAllAnalysed(final AnalysisContext analysisContext) {

    }
    
}
