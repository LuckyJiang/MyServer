package com.example.myserver.controller;


import com.example.myserver.entity.QuestionBank;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
public class ParamController {

    /**
     * 参数在url 或者query中
     * @param userId
     * @param testPaperId
     * @param userName
     */
    @GetMapping("/{userId}/{testPaperId}")
    public void get(@PathVariable("userId") final String userId,
                    @PathVariable("testPaperId") final String testPaperId,
                    @RequestParam(required = false, value = "userName")final String userName) {
    }

    /**
     * 接收多个String的list
     * @param ids
     * @return
     */
    @DeleteMapping
    public String delete(@RequestParam(value = "ids") final List<String> ids) {
        return "success";
    }

    /**
     * 接收body的参数，且参数为 实体对象
     * @param questionBank
     */
    @PostMapping("/save")
    public void save(@RequestBody final QuestionBank questionBank) {
    }

    /**
     * 接收body的参数，且参数为Map
     * @param paramMap
     */
    @PostMapping
    public void save(@RequestBody final Map<String, String> paramMap) {
        final String grade = paramMap.get("grade");
        final String gradeDetail = paramMap.get("gradeDetail");
        System.out.println("grade:" + grade);
        System.out.println("gradeDetail:" + gradeDetail);
    }

    /**
     * post 接收文件
     * @param file
     * @return
     */
    @PostMapping("upload")
    public String upload(final MultipartFile file) {
        return "success";
    }
}
