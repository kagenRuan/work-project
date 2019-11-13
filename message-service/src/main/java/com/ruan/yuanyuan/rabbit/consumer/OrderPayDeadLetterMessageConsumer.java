package com.ruan.yuanyuan.rabbit.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.ruan.yuanyuan.entity.OrderPay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-28
 * Time: 10:23
 * version:1.0
 * Description:支付订单死信队列消费
 */
@Component
public class OrderPayDeadLetterMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderPayDeadLetterMessageConsumer.class);

    /**
     * 死信队列监听
     */
    @RabbitListener(bindings = {@QueueBinding(
            value =@Queue(value = "${order.pay.dead.letter.consumer.queue}",durable = "true"),
            exchange = @Exchange(value = "${order.pay.dead.letter.consumer.exchange}",durable = "true"),
            key = {"${order.pay.dead.letter.consumer.routing.key}"}
            )
    })
    @RabbitHandler
    public void  deadMessage(@Payload OrderPay orderPay, Channel channel, @Headers Map<String,Object> headers) throws IOException {
        logger.info("<<<<<OrderPayDeadLetterMessageConsumer#deadMessage>>>>> 支付订单死信队列 参数 orderPay:{}", JSON.toJSONString(orderPay));
        logger.info("消息发送时间:{} 消息消费时间:{}",orderPay.getCreateTime(),new Date());
        Long deliveryTag =  (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }
}
