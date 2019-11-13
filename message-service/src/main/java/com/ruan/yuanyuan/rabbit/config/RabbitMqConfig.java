package com.ruan.yuanyuan.rabbit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-27
 * Time: 18:33
 * version:
 * Description: rabbitmq配置文件类
 */
@Configuration
@ComponentScan("com.ruan.yuanyuan.rabbit")
public class RabbitMqConfig {

    /**
     * 消息的手动ACK两种方式
     * 1、通过配置文件
     * 2、通过配置RabbitListenerContainerFactory设置
     */
//    @Bean
//    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//开启手动 ack
//        return factory;
//    }

}
