package com.ruan.yuanyuan;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: DubboSpiTest
 * @author: ruanyuanyuan
 * @date: 2019/12/12 16:01
 * @version: 1.0
 * @description: Dubbo的SPI扩展机制
 * SPI 扩展机制两大功能：AOP和注入
 **/
@EnableAspectJAutoProxy
public class DubboSpiTest {

    public static void main(String[] args) {
//        ExtensionLoader<TestDubboService>  extensionLoader = ExtensionLoader.getExtensionLoader(TestDubboService.class);
//        TestDubboService spi1 = extensionLoader.getExtension("spi1");
//        spi1.dubboService();

    }


}
