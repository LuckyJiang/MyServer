package com.example.myserver.service.impl;

import com.example.myserver.entity.Question;
import com.example.myserver.mapper.QuestionMapper;
import com.example.myserver.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
