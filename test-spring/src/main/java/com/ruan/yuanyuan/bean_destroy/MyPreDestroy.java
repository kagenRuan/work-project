package com.ruan.yuanyuan.bean_destroy;

import javax.annotation.PreDestroy;

/**
 * @ClassName: MyPreDestroy
 * @author: ruanyuanyuan
 * @date: 2020/6/16 14:13
 * @version: 1.0
 * @description:
 **/
public class MyPreDestroy {

    @PreDestroy
    public void destroy(){
        System.out.println("@PreDestroy==>注解销毁方法");
    }
}
