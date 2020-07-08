package com.ruan.yuanyuan;

import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.schema.DubboNamespaceHandler;
import com.alibaba.dubbo.registry.integration.RegistryProtocol;
import com.alibaba.dubbo.remoting.http.HttpBinder;
import com.alibaba.dubbo.remoting.transport.netty.NettyClient;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import com.alibaba.dubbo.rpc.protocol.http.HttpProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: TestDubboProviderAppliaction
 * @author: ruanyuanyuan
 * @date: 2019/12/12 12:06
 * @version: 1.0
 * @description:
 * 协议：
 * 1、如果是注册协议则是以：registry:开头
 * 2、服务协议：则是如dubbo: 或者 http:等开头的
 * provider注册服务流程：
 * 1、ServiceBean类
 *         onApplicationEvent()方法
 *            export()方法->ServiceConfig类实现
 *                              doExport()方法
 *                                  doExportUrls()方法
 *                                    doExportUrlsFor1Protocol()方法 TODO registry注册协议后添加一个key为export=dubbo协议
 *                                          export()方法->RegistryProtocol类实现
 *                                            doLocalExport()启动netty服务器->DubboProtocol类->NettyServer类 或者启动tomcat->HttpProtocol类->HttpServer类
 *                                                                          register()注册服务 TODO 通过zookeeper创建临时节点保存
 *
 **/
@SpringBootApplication
@EnableDubbo
public class TestDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDubboProviderApplication.class, args);
    }
}






//public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws java.lang.reflect.InvocationTargetException {
//    com.ruan.yuanyuan.service.TestDubboService w;
//    try {
//        w = ((com.ruan.yuanyuan.service.TestDubboService) $1);
//    } catch (Throwable e) {
//        throw new IllegalArgumentException(e);
//    }
//    try {
//        if ("dubboService".equals($2) && $3.length == 0) {
//            w.dubboService();
//            return null;
//        }
//    } catch (Throwable e) {
//        throw new java.lang.reflect.InvocationTargetException(e);
//    }
//    throw new com.alibaba.dubbo.common.bytecode.NoSuchMethodException("Not found method \"" + $2 + "\" in class com.ruan.yuanyuan.service.TestDubboService.");
//}

