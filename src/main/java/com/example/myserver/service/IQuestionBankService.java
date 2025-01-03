package com.example.myserver.service;

import com.example.myserver.common.entity.QuestionBank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题库表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-22
 */
public interface IQuestionBankService extends IService<QuestionBank> {

    List<Object> getAllAreaName();
}
