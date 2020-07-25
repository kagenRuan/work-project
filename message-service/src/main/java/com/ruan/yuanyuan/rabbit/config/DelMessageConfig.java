package com.ruan.yuanyuan.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 15:50
 * version:1.0
 * Description:死信队列配置
 * 死信交换机，死信交换机不是特定的交换机类型，而是普通的交互机
 * 根据TTL(time-to-live)消息过期策略，来实现消息的回收，被称为Dead-Letter-exchange （死信交换机DLX）
 * 一般消息变为死信，有以下三种情况：
 * 1.消息被拒绝消费（requeue=false）
 * 2.消息已经过期（TTL）
 * 3.队列达到最大长度
 */
@Component
public class DelMessageConfig {

    //死信队列消费端
    @Value("${order.pay.dead.letter.consumer.exchange}")
    private String TDL_ORDER_PAY_DEAD_LETTER_CONSUMER_EXCHANGE;
    //死信队列路由key
    @Value("${order.pay.dead.letter.consumer.routing.key}")
    private String TDL_ORDER_PAY_DEAD_LETTER_CONSUMER_ROUTING_KEY;

    //死信队列生产端
    //交换机
    @Value("${order.pay.dead.letter.provider.exchange}")
    private String TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_EXCHANGE;
    //队列
    @Value("${order.pay.dead.letter.provider.queue}")
    private String TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_QUEUE;
    //路由
    @Value("${order.pay.dead.letter.provider.routing.key}")
    private String TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_ROUTING_KEY;


    @Bean
    private DirectExchange orderPayDeadLetterDirectExchange() {
        return new DirectExchange(TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_EXCHANGE);
    }

    /**
     * 声明死信队列
     */
    @Bean
    public Queue orderPayDeadLetterQueue() {
        Map<String, Object> maps = new HashMap<>(2);
        maps.put("x-dead-letter-exchange", TDL_ORDER_PAY_DEAD_LETTER_CONSUMER_EXCHANGE);
        maps.put("x-dead-letter-routing-key", TDL_ORDER_PAY_DEAD_LETTER_CONSUMER_ROUTING_KEY);
        maps.put("x-message-ttl", 50000);
        return new Queue(TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_QUEUE, true, false, false, maps);
    }


    @Bean
    private Binding bindingOrderDirect() {
        return BindingBuilder.bind(orderPayDeadLetterQueue()).to(orderPayDeadLetterDirectExchange()).with(TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_ROUTING_KEY);
    }


}
