package com.ruan.yuanyuan.spring.beanFactory;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-09-30
 * @Time: 10:58
 * @version:1.0 1.0
 * @Description: BeanFactory
 */
public interface RBeanFactory {


    Object getBean(String name);

    <T> T getBean(Class<T> clazz);
}
