package com.example.myserver.service;

import com.example.myserver.common.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 试题表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-22
 */
public interface IQuestionService extends IService<Question> {

    List<Question> getByPointIds(List<String> pointIdList);
}
