package com.ruan.yuanyuan;

import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: TestDubboConsumerApplication
 * @author: ruanyuanyuan
 * @date: 2019/12/12 12:00
 * @version: 1.0
 * @description: dubbo 服务消费者
 * consumer引入服务目录流程：就是consumer保存服务提供者信息在本地
 * ReferenceBean类：->get()方法
 *        ReferenceConfig类->get()方法->init()方法->createProxy()方法
 *           dubboProtocol类中->refer()方法
 *        RegistryProtocol类中->refer()方法->doRefer()方法
 *consumer服务调用流程：
 *  ProtocolFilterWrapper->buildInvokerChain()方法
 *  InvokerInvocationHandler类->invoker()方法
 *  =================
 *  1、MockInvoker.invoker()：方法服务降级
 *  2、FailoverClusterInvoker.invoker()：集群容错
 *
 *
 **/
@SpringBootApplication
@EnableDubbo
public class TestDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDubboConsumerApplication.class, args);
    }
}
