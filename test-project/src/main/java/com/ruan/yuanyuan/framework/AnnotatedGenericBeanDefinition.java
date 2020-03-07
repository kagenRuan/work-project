package com.ruan.yuanyuan.framework;


import com.ruan.yuanyuan.framework.beanDefinition.AbstractBeanDefinition;

/**
 * @ClassName: AnnotatedGenericBeanDefinition
 * @author: ruanyuanyuan
 * @date: 2020/1/22 15:48
 * @version: 1.0
 * @description: Bean定义类
 **/
public class AnnotatedGenericBeanDefinition extends AbstractBeanDefinition {


    public AnnotatedGenericBeanDefinition(String beanName) {
        setBeanClass(beanName);
    }
}
