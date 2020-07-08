package com.ruan.yuanyuan.bean_init;

import javax.annotation.PostConstruct;

/**
 * @ClassName: MyPostCoutr
 * @author: ruanyuanyuan
 * @date: 2020/6/16 14:11
 * @version: 1.0
 * @description: 第二种 Bean对象的初始化，在SpringIOC容器将对象创建好并赋值后会调用此方法
 **/
public class MyPostConstruct {

    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct==>初始化");
    }
}
