package com.example.myserver.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author by Jiang Xiaomin
 * @desrc:
 */
@Configuration
public class FilePathConfig extends WebMvcConfigurerAdapter {

    /**
     * 访问磁盘上某个位置的文件时，需要 配置路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:tempFile/");
    }

}
