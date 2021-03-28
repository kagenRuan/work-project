package com.ruan.yuanyuan.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.ruan.yuanyuan.entity.RabbitMessage;
import com.ruan.yuanyuan.enums.MessageStatusEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqExchangeEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqRoutingKeyEnum;
import com.ruan.yuanyuan.mesage.vo.OrderMessageVo;
import com.ruan.yuanyuan.service.IMessageService;
import com.ruan.yuanyuan.vo.RabbitMessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-28
 * Time: 15:47
 * version:
 * Description: 消息发送
 * TODO 1、需要保证消息的幂等性，也是通过Mysql数据库实现
 * 2、保证消息不丢失这里通过Mysql数据库实现
 * 1、生产端在发送消息前需要保证消息已送达
 * 2、消费端在消费消息时需要到数据库验证消息状态
 */
@Component
public class RabbitMessageProvider implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageProvider.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IMessageService messageService;

    /**
     * 发送消息
     *
     * @param rabbitMqExchangeEnum   ExchangeEnum 枚举
     * @param rabbitMqRoutingKeyEnum Exchange 路由key没
     * @param obj                    数据体
     * @Description TODO 消息持久化
     */
    public void sendMessage(RabbitMqExchangeEnum rabbitMqExchangeEnum, RabbitMqRoutingKeyEnum rabbitMqRoutingKeyEnum, Object obj, CorrelationData correlationData) {
        logger.info("<<<<<<OrderRabbitMessageProvider#sendMessage>>>>>>>发送消息参数exchange:{} routingKey:{}", rabbitMqExchangeEnum.code, rabbitMqRoutingKeyEnum, JSON.toJSONString(obj));
        rabbitTemplate.convertAndSend(rabbitMqExchangeEnum.code, rabbitMqRoutingKeyEnum.code, obj, correlationData);
    }


    /**
     * 生产者将消息投递到交换机成功后的回调与队列没有直接接触
     * 消息确认送达方法
     *
     * @param correlationData 在发送消息时设置的消息唯一ID
     * @param hasAck          ACK=true仅仅标示消息已被Broker接收到，并不表示已成功投放至消息队列中  ACK=false表示消息由于Broker处理错误，消息并未处理成功
     * @param canes           错误原因
     * @Description TODO 根据消息ID修改消息的状态为已投递
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean hasAck, String canes) throws RuntimeException {
        logger.info("<<<<<<<OrderRabbitMessageProvider#confirm>>>>>>>消息送达唯一ID:{} 是否ACK:{} 错误原因:{}", correlationData.getId(), hasAck, canes);
        RabbitMessageVo message = new RabbitMessageVo();
        if (!hasAck) {
            logger.error("消息投递异常 参数 message:{} errorMsg:{}", JSON.toJSONString(message), canes);
            message.setErrorMsg(canes);
            message.setStatus(MessageStatusEnum.MESSAGE_DELIVERY_FAIL.code);
            messageService.updateByMessageId(message);
        } else {
            message.setStatus(MessageStatusEnum.CAN_DELIVERY.code);
        }
        message.setUpdateTime(new Date());
        boolean result = messageService.updateByMessageId(message);
        if (!result) {
            logger.error("RabbitMessageProvider#confirm 消息投递确认修改消息状态失败 参数 message:{}", JSON.toJSONString(message));
        }
    }

    /**
     * 交换机路由到队列失败才会回调
     * 消息路由不成功方法
     *
     * @param message
     * @param i
     * @param msg        错误描述
     * @param exchange   交换机
     * @param routingKey 路由key
     * @Description TODO 消息路由不成功也需要入库 消息状态为【MESSAGE_DELIVERY_FAIL】
     * 这里如何处理这种路由不成功的消息，看业务。比如可以根据投递次数达到一定量后放入到死信队列中
     */
    @Override
    public void returnedMessage(Message message, int i, String msg, String exchange, String routingKey) throws RuntimeException {
        OrderMessageVo orderMessageVo = (OrderMessageVo) rabbitTemplate.getMessageConverter().fromMessage(message);
        logger.info("<<<<<<OrderRabbitMessageProvider#returnedMessage>>>>>>>消息路由不成功 body:{} msg:{} exchange:{} routingKey:{}", JSON.toJSONString(orderMessageVo), msg, exchange, routingKey);
        RabbitMessageVo rabbitMessageVo = new RabbitMessageVo();
        rabbitMessageVo.setMessageId(orderMessageVo.getMessageId());
        rabbitMessageVo.setErrorMsg("NO_ROUTE:Message rout key not success");
        rabbitMessageVo.setStatus(MessageStatusEnum.MESSAGE_DELIVERY_FAIL.code);
        boolean result = messageService.updateByMessageId(rabbitMessageVo);
        if (!result) {
            logger.error("RabbitMessageProvider#returnedMessage 消息路由失败");
        }
    }

    /**
     * 为消息模板添加【消息确认送达回调方法】和【消息未路由成功方法】
     */
    @Override
    public void afterPropertiesSet() throws RuntimeException {
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
    }

}
