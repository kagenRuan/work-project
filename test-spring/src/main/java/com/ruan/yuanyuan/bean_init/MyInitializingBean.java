package com.ruan.yuanyuan.bean_init;

import org.springframework.beans.factory.InitializingBean;

/**
 * @ClassName: MyInitializingBean
 * @author: ruanyuanyuan
 * @date: 2020/6/16 13:47
 * @version: 1.0
 * @description: 第一种 Bean对象的初始化，在SpringIOC容器将对象创建好并赋值后会调用此方法
 **/
public class MyInitializingBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Spring Ioc 容器已创建完对象并且已赋值后调用==>MyInitializingBean类中的【afterPropertiesSet】方法");
    }
}
