package com.example.myserver.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtils.applicationContext == null) {
            SpringBeanUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 应用：final InitDataConfig initDataConfig = SpringBeanUtils.getBean(InitDataConfig.class);
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getBean(final Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
}
