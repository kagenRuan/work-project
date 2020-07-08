package com.ruan.yuanyuan.bean_init;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @ClassName: MyBeanProcessor
 * @author: ruanyuanyuan
 * @date: 2020/6/16 14:18
 * @version: 1.0
 * @description: 通过后置处理器对Bean处理
 **/
public class MyBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("d".equals(beanName)){
            System.out.println("postProcessBeforeInitialization==》如果容器中有Bean对象【d】则打印词句");
        }
        return null;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("d".equals(beanName)){
            System.out.println("postProcessAfterInitialization==》如果容器中有Bean对象【d】则打印词句");
        }
        return null;
    }
}
