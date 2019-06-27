package com.example.dmy.kafkastorm.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author: daimengying
 * @Date: 2019/1/3 17:34
 * @Description:动态获取bean
 */
public class GetSpringBean implements ApplicationContextAware {
    private static ApplicationContext context;

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(Class<T> c) {

        return context.getBean(c);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if(applicationContext!=null){
            context = applicationContext;
        }
    }
}
