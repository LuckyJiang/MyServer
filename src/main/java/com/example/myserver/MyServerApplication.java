package com.example.myserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.myserver.mapper")
public class MyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyServerApplication.class, args);
    }

}
