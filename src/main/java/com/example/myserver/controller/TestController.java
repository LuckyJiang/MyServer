package com.example.myserver.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@RestController
@Api(tags = "集成Knife模块")
@RequestMapping("/test")
public class TestController {
    
    @ApiOperation("测试接口")
    @GetMapping
    public String upload() {
        return "Hello Welcome to 我的世界！";
    }
}
