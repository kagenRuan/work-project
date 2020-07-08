package com.ruan.yuanyuan.beanFactory_init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @ClassName: MyBeanFactory
 * @author: ruanyuanyuan
 * @date: 2020/6/16 15:39
 * @version: 1.0
 * @description: 实现BeanFactoryPostProcessor接口对BeanFactory进行二次初始化
 **/
public class MyBeanFactory implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactory已经初始化完成，此时用户可以实现BeanFactoryPostProcessor接口对BeanFactory进行二次初始化");
    }
}
