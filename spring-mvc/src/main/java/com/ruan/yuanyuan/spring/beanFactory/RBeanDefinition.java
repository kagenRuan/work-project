package com.ruan.yuanyuan.spring.beanFactory;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-09-27
 * @Time: 15:05
 * @version:1.0
 * @Description:
 */
public class RBeanDefinition<T> {

     private Class<T> beanClass;

     private String name;

    public Class<T> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
