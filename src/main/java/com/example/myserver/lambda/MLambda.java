package com.example.myserver.lambda;


import com.example.myserver.common.entity.QuestionBank;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public class MLambda {

    /**
     * 解决：filter 后没有 匹配道对象，如果直接调用get方法会报错。
     * 通过OrElse(null)方法，返回一个null对象。
     */
    public void filter(List<QuestionBank> banks) {
        final QuestionBank findData = banks.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        System.out.println(findData);
    }

    /**
     * 方法1：将list转换为mp;当key重复时，会报错。
     * @param banks
     */
    public void list2map(List<QuestionBank> banks) {
        Map<String, QuestionBank> map =  banks.stream()
                .collect(Collectors.toMap(QuestionBank::getId, obj -> obj));
    }

    /**
     * 方法2：将list转换为mp;当key重复时，会覆盖掉之前的值。
     * @param banks
     */
    public void list2map2(List<QuestionBank> banks) {
        Map<String, QuestionBank> map =  banks.stream()
                .collect(Collectors.toMap(QuestionBank::getId, obj -> obj, (k1, k2) -> k1));
    }


    /**
     * 从对象list中提取某个属性的list
     * @param banks
     */
    public void objectListToStringList(List<QuestionBank> banks) {
        List<String> nameLists = banks.stream()
                .filter(Objects::nonNull)
                .map(QuestionBank::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * list按照code字段进行分组
     * @param banks
     * @return
     */
    public Map<String, List<QuestionBank>> groupList(List<QuestionBank> banks) {
        return banks.stream()
                .collect(Collectors.groupingBy(QuestionBank::getCode));
    }

}
