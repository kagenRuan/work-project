package com.ruan.yuanyuan.spring.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestBeanPostProcessor
 * @Author ruanyuanyuan
 * @Date 2020/8/28-16:02
 * @Version 1.0
 * @Description TODO 这里主要是前面已经将BeanFactory进行初始化，然后用户就可以通过实现BeanFactoryPostProcessor接口
 *                   对BeanFactory进行二次初始化
 **/
@Component
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.err.println("bean factory 二次初始化");
    }
}
