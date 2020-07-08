package com.ruan.yuanyuan.beanFactory_init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @ClassName: MyBeanFactoryRegister
 * @author: ruanyuanyuan
 * @date: 2020/6/16 16:17
 * @version: 1.0
 * @description:
 **/
public class MyBeanFactoryRegister implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("实现了BeanDefinitionRegistryPostProcessor接口==》postProcessBeanDefinitionRegistry");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("实现了BeanDefinitionRegistryPostProcessor接口==》postProcessBeanFactory");
    }
}
