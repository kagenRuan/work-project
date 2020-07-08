package com.ruan.yuanyuan.factory_bean;

import com.ruan.yuanyuan.entry.E;
import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName: MyFactoryBean
 * @author: ruanyuanyuan
 * @date: 2020/6/16 21:22
 * @version: 1.0
 * @description:
 **/
public class MyFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return new E();
    }

    @Override
    public Class<?> getObjectType() {
        return E.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
