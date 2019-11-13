package com.ruan.yuanyuan.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-29
 * Time: 13:51
 * version:1.0
 * Description:获取springBean工具
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * 返回Bean对象
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName){
       return (T) applicationContext.getBean(beanName);
    }

    /**
     * 返回Bean对象
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> obj){
        return applicationContext.getBean(obj);
    }
}
