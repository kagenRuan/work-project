package com.ruan.yuanyuan.spring.beanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestBeanPostProcessor
 * @Author ruanyuanyuan
 * @Date 2020/8/28-19:58
 * @Version 1.0
 * @Description TODO
 **/
@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.err.println(beanName+":执行BeanPostProcessor=>postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.err.println(beanName+":执行BeanPostProcessor=>postProcessAfterInitialization");
        return bean;
    }
}
