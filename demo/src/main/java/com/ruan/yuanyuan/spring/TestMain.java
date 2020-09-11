package com.ruan.yuanyuan.spring;

import com.ruan.yuanyuan.spring.config.TestConfig;
import com.ruan.yuanyuan.spring.controller.TestController;
import com.ruan.yuanyuan.spring.listener.TestApplicationEvent;
import com.ruan.yuanyuan.spring.service.TestSayService;
import com.ruan.yuanyuan.spring.service.impl.TestSayServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName TestMain
 * @Author ruanyuanyuan
 * @Date 2020/8/28-15:15
 * @Version 1.0
 * @Description TODO
 **/
public class TestMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
        annotationConfigApplicationContext.refresh();
        TestSayServiceImpl testSayService = annotationConfigApplicationContext.getBean(TestSayServiceImpl.class);
        testSayService.sayHello();
        //Spring事件
//        TestApplicationEvent event = new TestApplicationEvent("Object","1","yy");
//        annotationConfigApplicationContext.publishEvent(event);
    }
}
