package com.ruan.yuanyuan.rabbit.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.ruan.yuanyuan.mesage.vo.OrderMessageVo;
import com.ruan.yuanyuan.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-20
 * Time: 16:21
 * version:
 * Description: 订单消息消费中心
 */
@Component
public class OrderMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderMessageConsumer.class);


    /**
     * 订单消息消费方法
     *
     * @param orderMessageVo 接收到的消息体
     * @param channel        通道
     * @param headers        消息header
     * @throws IOException
     * @Description TODO 监听到消息时需要根据消息ID到数据库查询消息，主要是做消息的幂等也就是消息的重复消费
     * 同时还需要将消息的状态改为消费成功MessageStatusEnum枚举中
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "${order.rabbitmq.queue.name}", durable = "${order.rabbitmq.queue.durable}"),
            exchange = @Exchange(value = "${order.rabbitmq.exchange.name}", durable = "${order.rabbitmq.exchange.durable}", ignoreDeclarationExceptions = "${order.rabbitmq.exchange.ignoreDeclarationExceptions}"),
            key = "${order.rabbitmq.routing.key}"
    )
    })
    @RabbitHandler
    public void onOrderMessage(@Payload OrderMessageVo orderMessageVo, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        logger.info("<<<<<<<<OrderMessageConsumer#onOrderMessage>>>>>>>>订单消息消费方法 orderMessage:{}", JSON.toJSONString(orderMessageVo));
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            // TODO 这里还需要对消息做幂等性
            /**
             * 手动ACK
             * deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
             */
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 出现异常将消息重新放回mq中，不要将消息重新放在队列的头部，一定要放在尾部防止出现死循环
             * deliveryTag：消息index
             * b:是否批量. true：将一次性拒绝所有小于deliveryTag的消息
             * b1:是否重回队列。true将消息放入队列false废弃
             */
            channel.basicNack(deliveryTag, false, true);
        }

    }

}
