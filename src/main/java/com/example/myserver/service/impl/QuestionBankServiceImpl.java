package com.example.myserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myserver.common.entity.QuestionBank;
import com.example.myserver.mapper.QuestionBankMapper;
import com.example.myserver.service.IQuestionBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 题库表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-22
 */
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements IQuestionBankService {

    /**
     * 查询所有题库的名称，并按照名称进行排序：如果名称中有数字，则按照阿拉伯数据排序
     * @return
     */
    @Override
    public List<Object> getAllAreaName() {
        final QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<>();
        String customOrderClause = getStringOfMysql();
        queryWrapper.select("NAME")
                .last(customOrderClause);
        return baseMapper.selectObjs(queryWrapper).stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 这个方法针对是的 Oracle 数据库
     * @return
     */
    private static String getStringOfOracle() {
        String customOrderClause = "ORDER BY CASE " +
                "WHEN REGEXP_LIKE(NAME, '^[0-9]+.*') THEN TO_NUMBER(REGEXP_SUBSTR(NAME, '^[0-9]+')) " +
                "ELSE ASCII(SUBSTR(NAME, 1, 1)) END, NAME";
        return customOrderClause;
    }

    /**
     * 这个方法针对是的 Mysql 数据库
     * @return
     */
    private static String getStringOfMysql() {
        String customOrderClause =  "ORDER BY " +
                "CAST(NULLIF(SUBSTRING_INDEX(NAME, REGEXP_REPLACE(NAME, '[^0-9]', ''), -1), '') AS SIGNED), " +
                "REGEXP_REPLACE(NAME, '[0-9]', '')";
        return customOrderClause;
    }
}
