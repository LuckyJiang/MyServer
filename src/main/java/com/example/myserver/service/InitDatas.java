package com.example.myserver.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;


@Service
public class InitDatas implements ApplicationRunner {

    
    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("服务启动后执行该方法");
    }

}
