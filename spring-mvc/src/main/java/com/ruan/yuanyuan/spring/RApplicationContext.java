package com.ruan.yuanyuan.spring;

import com.ruan.yuanyuan.spring.beanFactory.RBeanDefinition;
import com.ruan.yuanyuan.spring.beanFactory.RDefaultListableBeanFactory;
import com.ruan.yuanyuan.util.StringUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-09-27
 * @Time: 14:42
 * @version:1.0
 * @Description: spring
 */
public class RApplicationContext {

    private RDefaultListableBeanFactory rDefaultListableBeanFactory = new RDefaultListableBeanFactory();


    /**
     * 事件监听器
     */
    private Set<ApplicationListener<?>> earlyApplicationListeners;
    /**
     * 事件派发器
     */
    private Set<ApplicationEvent> earlyApplicationEvents;


    public RApplicationContext(Class<?>... annotatedClasses) {

        register(annotatedClasses);

        refresh();

    }

    /**
     * 扫描注解配置文件，将配置文件的Bean的定义信息放入到BeanDefinitionMap容器中
     *
     * @param annotatedClasses 配置文件
     */
    private void register(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBeanDefinition(annotatedClass);
        }
    }

    /**
     * 直接注册Bean的定义信息到容器中
     *
     * @param annotatedClass
     */
    private void registerBeanDefinition(Class<?> annotatedClass) {
        String beanName = StringUtils.loverFirstChar(annotatedClass.getSimpleName());
        RBeanDefinition beanDefinition = new RBeanDefinition();
        beanDefinition.setBeanClass(annotatedClass);
        beanDefinition.setName(StringUtils.loverFirstChar(annotatedClass.getSimpleName()));
        rDefaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
    }


    /**
     * 初始化IOC容器
     */
    private void refresh() {
        /**
         * 准备刷新上下文，初始化事件监听器和事件派发器
         */
        prepareRefresh();
        /**
         * 为BeanFactory设置唯一ID
         */
        rDefaultListableBeanFactory = obtainFreshBeanFactory();

        /**
         * 初始化单例Bean,注意是单例Bean,对于layz和propertory不进行实例化
         */
        finishBeanFactoryInitialization(rDefaultListableBeanFactory);

    }


    private void prepareRefresh() {
        earlyApplicationListeners = new LinkedHashSet<>();
        earlyApplicationEvents = new LinkedHashSet<>();
    }

    private RDefaultListableBeanFactory obtainFreshBeanFactory() {
        rDefaultListableBeanFactory.setSerializationId(UUID.randomUUID().toString());
        return rDefaultListableBeanFactory;
    }


    private void finishBeanFactoryInitialization(RDefaultListableBeanFactory rDefaultListableBeanFactory) {
        rDefaultListableBeanFactory.preInstantiateSingletons();
    }


    public Object getBean(String name) {
        Object obj = rDefaultListableBeanFactory.getBean(name);
        return obj;
    }
}
