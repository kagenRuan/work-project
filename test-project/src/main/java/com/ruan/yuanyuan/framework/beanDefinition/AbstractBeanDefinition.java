package com.ruan.yuanyuan.framework.beanDefinition;

import org.springframework.lang.Nullable;

/**
 * @ClassName: AbstractBeanDefinition
 * @author: ruanyuanyuan
 * @date: 2020/1/22 16:28
 * @version: 1.0
 * @description: Bean定义抽象类
 **/
public abstract class AbstractBeanDefinition implements BeanDefinition {

    @Nullable
    private volatile Object beanClass;

    private boolean lazyInit = false;

    @Nullable
    private String factoryBeanName;

    @Nullable
    public Object getBeanClass() {
        return beanClass;
    }

    @Override
    public void setBeanClass(@Nullable Object beanClass) {
        this.beanClass = beanClass;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Nullable
    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(@Nullable String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
