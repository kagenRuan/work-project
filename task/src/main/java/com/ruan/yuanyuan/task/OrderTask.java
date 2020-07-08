package com.ruan.yuanyuan.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruan.yuanyuan.dto.ProductDto;
import com.ruan.yuanyuan.entity.Order;
import com.ruan.yuanyuan.entity.OrderPay;
import com.ruan.yuanyuan.entity.RabbitMessage;
import com.ruan.yuanyuan.enums.MessageStatusEnum;
import com.ruan.yuanyuan.enums.MessageTypeEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqExchangeEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqRoutingKeyEnum;
import com.ruan.yuanyuan.rabbitmq.RabbitMessageProvider;
import com.ruan.yuanyuan.service.IMessageService;
import com.ruan.yuanyuan.service.IOrderPayService;
import com.ruan.yuanyuan.service.IOrderService;
import com.ruan.yuanyuan.util.MessageIdCorrelationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-21
 * Time: 15:34
 * version:1.0
 * Description:主要定时获取redis中的数据，将一部分数据放入到list中同一批量修改，这样可以减少数据库的IO，注意同时需要删除redis中的数据
 */
@Component
@Configuration
@EnableScheduling
public class OrderTask {

    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private IMessageService messageService;
    @Autowired
    private RabbitMessageProvider rabbitMessageProvider;


    /**
     * 每10秒查询一次消息添加成功但是业务逻辑没有执行成功的。
     */
    @Scheduled(cron = "0/10 * * * * ?")
    private void configureTasks() {
        /**
         * 这里查询消息还需要查询时间间隔，否则会出现当正在被消费，但是定时任务也把它查询出来了
         */
        QueryWrapper messageWrapper = new QueryWrapper<>();
        messageWrapper.eq("id", "60cef1999eb6839607b9c12b80fd58ff");
        List<RabbitMessage> messageList = Optional.of(messageService.list(messageWrapper)).orElse(new ArrayList<>());
        messageList.stream().forEach(obj -> {
            RabbitMqExchangeEnum exchangeEnum = RabbitMqExchangeEnum.getRabbitMqExchangeEnum(obj.getExchange());
            RabbitMqRoutingKeyEnum routingKeyEnum = RabbitMqRoutingKeyEnum.getRabbitMqExchangeEnum(obj.getRoutingKey());
            //每次投递,投递次数加1
            obj.setRetryNum(obj.addRetryNum());
            String body = obj.getBody();
            //构造查询条件
            QueryWrapper query = new QueryWrapper<>();
            query.eq("filed1", obj.getFiled1());
            switch (MessageTypeEnum.getMessageTypeEnum(obj.getMessageType())) {
                case ORDER_PAY:
                    OrderPay orderPay = JSON.parseObject(body, OrderPay.class);
                    if (!ObjectUtils.isEmpty(orderPay)) {
                        CorrelationData correlationData = new CorrelationData();
                        correlationData.setId(orderPay.getPaySn());
                        //如果当前消息已经重试投递次数大于等于5，则直接将其丢进对应的死信队列中，有死信队列根据业务逻辑处理
                        if (5 == obj.getRetryNum()) {
                            RabbitMessage rabbitMessage = new RabbitMessage();
                            BeanUtils.copyProperties(obj, rabbitMessage);
                            rabbitMessage.setId("");
                            rabbitMessage.setRetryNum(0);
                            rabbitMessage.setExchange(RabbitMqExchangeEnum.TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_EXCHANGE.code);
                            rabbitMessage.setRoutingKey(RabbitMqRoutingKeyEnum.TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_ROUTING_KEY.code);
                            rabbitMessage.setCreateTime(new Date());
                            rabbitMessage.setUpdateTime(new Date());
                            rabbitMessage.setStatus(MessageStatusEnum.MESSAGE_EXCEPTION.code);
                            boolean result = messageService.save(rabbitMessage);
                            if (!result) {
                                logger.error("OrderTask#configureTasks save message fail parameter:{}", JSON.toJSONString(rabbitMessage));
                                break;
                            }
                            boolean delete = messageService.removeById(obj.getId());
                            if (!delete) {
                                logger.error("OrderTask#configureTasks delete message fail parameter:{}", JSON.toJSONString(obj.getId()));
                                break;
                            }
                            rabbitMessageProvider.sendMessage(RabbitMqExchangeEnum.TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_EXCHANGE, RabbitMqRoutingKeyEnum.TDL_ORDER_PAY_DEAD_LETTER_PROVIDER_ROUTING_KEY, orderPay, correlationData);
                            break;
                        }
                        messageService.updateById(obj);
                        rabbitMessageProvider.sendMessage(exchangeEnum, routingKeyEnum, orderPay, correlationData);
                    }
                default:
                    break;
            }

        });

    }

}
