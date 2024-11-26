package com.example.myserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.myserver.entity.Question;
import com.example.myserver.mapper.QuestionMapper;
import com.example.myserver.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 试题表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-22
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
    
    private static final Integer MAX_COUNT = 1000;

    /**
     * MybatisPlus：实现根据id列表筛选数据，列表长度不能超过1000
     * @param pointIdList
     * @return
     */
    @Override
    public List<Question> getByPointIds(final List<String> pointIdList) {
        final List<List<String>> partition = Lists.partition(pointIdList, MAX_COUNT);
        final LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        for (List<String> strings : partition) {
            queryWrapper.or().in(Question::getId, strings);
        }
        return list(queryWrapper);
    }
}
