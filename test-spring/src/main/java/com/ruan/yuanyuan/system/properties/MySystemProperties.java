package com.ruan.yuanyuan.system.properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName: MySystemProperties
 * @author: ruanyuanyuan
 * @date: 2020/6/16 14:45
 * @version: 1.0
 * @description:
 **/
public class MySystemProperties extends ClassPathXmlApplicationContext {

    @Override
    protected void initPropertySources() {
        System.out.println("获取系统参数===========================start");
        Map<String, Object>  systemProperties = getEnvironment().getSystemProperties();
        systemProperties.forEach((k,v) -> System.out.println("systemProperties【key="+k+"v="+v+"】"));

        MutablePropertySources propertySources = getEnvironment().getPropertySources();
        propertySources.forEach(obj -> System.out.println("propertySources  【"+obj+"】"));

        Map<String, Object> SystemEnvironment = getEnvironment().getSystemEnvironment();
        SystemEnvironment.forEach((k,v) -> System.out.println("SystemEnvironment【key="+k+"v="+v+"】"));

        String[] defaultProfiles = getEnvironment().getDefaultProfiles();
        Arrays.stream(defaultProfiles).forEach(obj -> System.out.println("defaultProfiles 【"+obj+"】"));
        System.out.println("获取系统参数===========================end");


    }

}
