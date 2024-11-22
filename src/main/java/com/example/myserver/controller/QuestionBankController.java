package com.example.myserver.controller;


import com.example.myserver.service.impl.QuestionBankServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 题库表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-22
 */
@Api(tags = "题库管理")
@RestController
@RequestMapping("/question-bank")
public class QuestionBankController {
    
    @Autowired
    private QuestionBankServiceImpl bankService;
    
    @ApiOperation("获取所有仓库名称并排序")
    @GetMapping
    public List<Object> getAllAreaName()
    {
        return bankService.getAllAreaName();
    }

}
