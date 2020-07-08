package com.ruan.yuanyuan.bean_destroy;

import org.springframework.beans.factory.DisposableBean;

/**
 * @ClassName: bean_destry
 * @author: ruanyuanyuan
 * @date: 2020/6/16 14:04
 * @version: 1.0
 * @description: 第一种：销毁Bean通知方法
 **/
public class MyDestroy implements DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean==>对象销毁调用此方法");
    }
}
