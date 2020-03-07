package com.ruan.yuanyuan.framework.beanDefinition;


import org.springframework.lang.Nullable;

/**
 * @ClassName: BeanDefinition
 * @author: ruanyuanyuan
 * @date: 2020/1/22 15:59
 * @version: 1.0
 * @description: Bean定义接口
 **/
public interface BeanDefinition {

    void setBeanClass(@Nullable Object beanClass);

    void setLazyInit(boolean lazyInit);




}
