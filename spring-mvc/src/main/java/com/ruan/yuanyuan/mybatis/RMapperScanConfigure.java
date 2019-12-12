package com.ruan.yuanyuan.mybatis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.util.Assert;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-10-02
 * @Time: 23:00
 * @version:1.0
 * @Description:
 */
public class RMapperScanConfigure implements BeanDefinitionRegistryPostProcessor, InitializingBean {

    private String basePackage;//mybatis包路径

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isNull(basePackage, "this basePackage not null");
    }
}
