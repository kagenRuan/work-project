package com.ruan.yuanyuan.spring.factoryBean;

import com.ruan.yuanyuan.spring.entity.TestSayFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestFactoryBean
 * @Author ruanyuanyuan
 * @Date 2020/8/28-21:00
 * @Version 1.0
 * @Description TODO
 **/
@Component
public class TestFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return new TestSayFactoryBean("2","yy");
    }

    @Override
    public Class<?> getObjectType() {
        return TestSayFactoryBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
