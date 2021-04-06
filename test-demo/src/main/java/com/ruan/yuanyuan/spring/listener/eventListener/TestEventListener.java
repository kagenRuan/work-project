package com.ruan.yuanyuan.spring.listener.eventListener;

import com.ruan.yuanyuan.spring.config.TestConfig;
import com.ruan.yuanyuan.spring.listener.TestApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @ClassName TestEventListener
 * @Author ruanyuanyuan
 * @Date 2021/1/5-15:14
 * @Version 1.0
 * @Description TODO 主要是测试@EventListener注解使用的
 **/

@Configuration
public class TestEventListener {

    @EventListener
    public void listen(TestApplicationEvent event) {
        System.out.println("接收到事件：" + event);
    }
}

class TestMainEventListener{

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
        //Spring事件
        TestApplicationEvent event = new TestApplicationEvent("Object","1","yy");
        annotationConfigApplicationContext.publishEvent(event);
    }
}
