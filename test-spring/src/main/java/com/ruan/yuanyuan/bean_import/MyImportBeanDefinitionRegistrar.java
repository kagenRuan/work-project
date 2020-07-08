package com.ruan.yuanyuan.bean_import;

import com.ruan.yuanyuan.entry.D;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName: D
 * @author: ruanyuanyuan
 * @date: 2020/6/16 13:40
 * @version: 1.0
 * @description: 用于实验实现ImportBeanDefinitionRegistrar接口进行注入Bean对象【D】
 **/
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(D.class);
        registry.registerBeanDefinition("d",beanDefinition);
    }
}
