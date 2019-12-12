package com.ruan.yuanyuan.rabbit.consumer;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import com.ruan.yuanyuan.entity.*;
import com.ruan.yuanyuan.enums.Yum;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IBuyerService;
import com.ruan.yuanyuan.service.IMessageService;
import com.ruan.yuanyuan.service.IPowerEtcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Map;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 11:21
 * version:1.0
 * Description:支付订单消息监听
 */
@Component
public class OrderPayMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderPayMessageConsumer.class);

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IPowerEtcService powerEtcService;
    @Autowired
    private IBuyerService buyerService;

    /**
     * 支付订单消息消费方法
     *
     * @param orderPay 接收到的消息体
     * @param channel  通道
     * @param headers  消息header
     * @throws IOException
     * @Description TODO 1、首先
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "${order.pay.rabbitmq.queue.name}", durable = "${order.pay.rabbitmq.queue.durable}"),
            exchange = @Exchange(value = "${order.pay.rabbitmq.exchange.name}", durable = "${order.pay.rabbitmq.exchange.durable}"),
            key = "${order.pay.rabbitmq.routing.key}"
    )
    })
    @RabbitHandler
    public void onOrderMessage(@Payload OrderPay orderPay, Channel channel, @Headers Map<String, Object> headers) throws IOException {
        logger.info("<<<<<<<<OrderPayMessageConsumer#onOrderMessage>>>>>>>>订单消息消费方法 orderMessage:{}", JSON.toJSONString(orderPay));
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        /**
         * TODO 消费逻辑
         *  1、判断该消息是否已经消费，将消息ID保存到幂等表中，消费时先去表中查询是否有该消息数据，有则表示已经消费，否则表示没有消费过，则执行业务逻辑
         *  2、扣款账户金额 这里需要使用到TCC
         *  3、扣库存
         *  4、发货
         *  5、通知商户
         */
        QueryWrapper<RabbitMessage> query = new QueryWrapper();
        String messageId = headers.get("messageId").toString();
        query.eq("message_id", messageId);
        RabbitMessage rabbitMessage = messageService.getOne(query);

        try {
            /**
             * TODO 消息幂等以及业务逻辑处理方法
             */
            this.business(rabbitMessage, orderPay);
            /**
             * 手动ACK
             * deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
             */
            channel.basicAck(deliveryTag, false);
            if (!ObjectUtils.isEmpty(rabbitMessage)) {
                boolean deleteMessage = messageService.removeById(rabbitMessage.getId());
                if (!deleteMessage) {
                    logger.error("OrderPayMessageConsumer#onOrderMessage delete message fail  parameter:{}", rabbitMessage.getId());
                }
            }
        } catch (Exception e) {
            /**
             * 出现异常将消息重新放回mq中，不要将消息重新放在队列的头部，一定要放在尾部防止出现死循环
             * deliveryTag：消息index
             * b:是否批量. true：将一次性拒绝所有小于deliveryTag的消息
             * b1:是否重回队列。true将消息放入队列false废弃
             */

            //channel.basicReject(deliveryTag,false);//这个方法主要是拒绝一条消息
            channel.basicNack(deliveryTag, false, false);//这个方法可以拒绝多条消息
        }

    }


    @Transactional(rollbackFor = Exception.class)
    void business(RabbitMessage rabbitMessage, OrderPay orderPay) {
        QueryWrapper<PowerEtc> powerQuery = new QueryWrapper();
        powerQuery.eq("message_id", rabbitMessage.getId());
        powerQuery.eq("is_valid", Yum.YES.getCode());
        //根据消息ID查询幂等表
        PowerEtc powerEtc = powerEtcService.getOne(powerQuery);
        if (!ObjectUtils.isEmpty(powerEtc)) {
            return;
        }
        Buyer buyer = buyerService.getById(orderPay.getBuyerId());
        if (ObjectUtils.isEmpty(buyer)) {
            logger.error("OrderPayMessageConsumer#onOrderMessage query buyer error parameter:{}", JSON.toJSONString(buyer));
            BusinessAssert.notNull(buyer, ExceptionUtil.BuyerExceptionEnum.BUYER_FIND_FAIL);
        }
        //扣款
        buyer.setAmount(buyer.getAmount().subtract(orderPay.getAmount()));
        boolean result = buyerService.updateById(buyer);
        if (!result) {
            logger.error("OrderPayMessageConsumer#onOrderMessage buyer amount deduct fail  buyerAmount:{}  deductAmount:{}", buyer.getAmount(), orderPay.getAmount());
            BusinessAssert.isTrue(result, ExceptionUtil.BuyerExceptionEnum.BUYER_AMOUNT_DEDUCT_FAIL);
        }
        //扣库存
        //加积分
        /**
         * TODO 如果没有查询到幂等表中的数据，则可以执行逻辑
         */
        System.out.println("业务逻辑处理");
        PowerEtc savePowerEtc = new PowerEtc();
        savePowerEtc.setMessageId(rabbitMessage.getMessageId());
        boolean resultPowerEtc = powerEtcService.save(savePowerEtc);
        if (!resultPowerEtc) {
            logger.error("OrderPayMessageConsumer#onOrderMessage save powerEtc fail powerEtc:{}", JSON.toJSONString(savePowerEtc));
            BusinessAssert.isTrue(resultPowerEtc, ExceptionUtil.PowerEtcExceptionEnum.POWER_SAVE_FAIL);
        }
    }


}
