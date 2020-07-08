package com.ruan.yuanyuan.configuration;

import com.ruan.yuanyuan.beanFactory_init.MyBeanFactory;
import com.ruan.yuanyuan.beanFactory_init.MyBeanFactoryRegister;
import com.ruan.yuanyuan.bean_destroy.MyDestroy;
import com.ruan.yuanyuan.bean_destroy.MyPreDestroy;
import com.ruan.yuanyuan.bean_init.MyBeanProcessor;
import com.ruan.yuanyuan.bean_init.MyInitializingBean;
import com.ruan.yuanyuan.bean_import.MyImportBeanDefinitionRegistrar;
import com.ruan.yuanyuan.bean_import.MyImportSelector;
import com.ruan.yuanyuan.entry.A;
import com.ruan.yuanyuan.bean_init.MyPostConstruct;
import com.ruan.yuanyuan.factory_bean.MyFactoryBean;
import com.ruan.yuanyuan.system.properties.MySystemProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @ClassName: MyImport
 * @author: ruanyuanyuan
 * @date: 2020/6/16 13:21
 * @version: 1.0
 * @description:
 **/
@Configuration
@Import({A.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MyConfiguration {

    /**
     * InitializingBean 接口对象初始化
     */
    @Bean
    public MyInitializingBean myInitializingBean(){
        return new MyInitializingBean();
    }

    /**
     * @PostConstruct注解对象初始化
     */
    @Bean
    public MyPostConstruct myPostConstruct(){
        return new MyPostConstruct();
    }

    /**
     * MyDestroy接口销毁Bean
     */
    @Bean
    public MyDestroy myDestroy(){
        return new MyDestroy();
    }

    /**
     * @PreDestroy注解对象销毁
     */
    @Bean
    public MyPreDestroy myPreDestroy(){
        return new MyPreDestroy();
    }

    /**
     * 通过实现BeanPostProcessor接口也可以对象Bean对象进行操作
     * @return
     */
    @Bean
    public MyBeanProcessor myBeanProcessor(){
        return new MyBeanProcessor();
    }

    /**
     * 获取系统参数以及环境
     * @return
     */
//    @Bean
//    public MySystemProperties mySystemProperties (){
//       return new MySystemProperties();
//    }
    /**
     * BeanFactoryPostProcessor的子接口，首先会执行实现了BeanDefinitionRegistryPostProcessor接口的
     */
    @Bean
    public MyBeanFactoryRegister myBeanFactoryRegister(){
        return new MyBeanFactoryRegister();
    }

    /**
     * 在执行实现了BeanFactoryProcessor接口的类
     * BeanFactory已经被Spring初始化完成，此处实现BeanFactoryProcessor接口对BeanFactory进行二次初始化
     */
    @Bean
    public MyBeanFactory myBeanFactory(){
        return new MyBeanFactory();
    }

    /**
     * 实现了FactoryBean接口的类
     * @return MyFactoryBean
     */
    @Bean
    public MyFactoryBean myFactoryBean(){
        return new MyFactoryBean();
    }



}
