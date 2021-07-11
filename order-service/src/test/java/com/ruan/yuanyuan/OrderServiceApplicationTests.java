package com.ruan.yuanyuan;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruan.yuanyuan.dto.ProductDto;
import com.ruan.yuanyuan.entity.*;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.mesage.enums.RabbitMqExchangeEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqQueueEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqRoutingKeyEnum;
import com.ruan.yuanyuan.rabbitmq.RabbitMessageProvider;
import com.ruan.yuanyuan.service.*;
import com.ruan.yuanyuan.util.CollectorsUtil;
import com.ruan.yuanyuan.util.MessageIdCorrelationData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.ruan.yuanyuan.dao")
public class OrderServiceApplicationTests {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderPayService orderPayService;
    @Autowired
    private RabbitMessageProvider rabbitMessageProvider;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IZuulApiRouteService service;

    @Test
    public void queryOrder() throws Exception {
        Order order = orderService.queryById("1");
        System.out.println(order.toString());
    }

    @Test
    public void createOrder() throws Exception {
        //创建订单
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        productDto.setShopId("123456");
        productDto.setPrice(new BigDecimal(10));
        productDto.setNum(2);
        productDto.setId("123456");
        ProductDto productDto1 = new ProductDto();
        productDto1.setShopId("1234567");
        productDto1.setPrice(new BigDecimal(20));
        productDto1.setNum(3);
        productDto1.setId("1234567");
        productDtoList.add(productDto1);
        productDtoList.add(productDto);
        List<Order> orderList = orderService.createOrder(productDtoList);


        //模拟调用支付
        Thread.sleep(10000);//10000
        //创建支付订单
        OrderPay orderPay = orderPayService.create(orderList.stream().map(obj -> obj.getId()).collect(Collectors.toList()));
        Thread.sleep(10000);
        /**
         * 支付成功后进行消息通知
         * TODO 对于第三方的消息通知只需要先保存消息，然后在发送消息到本地mq中。
         *      消息异常测试：1、生产端投递失败
         *                  2、消费端
         */
        RabbitMessage message = new RabbitMessage();
        message.setStatus("1");
        message.setExchange(RabbitMqExchangeEnum.ORDER_PAY_EXCHANGE.code);
        message.setRoutingKey(RabbitMqRoutingKeyEnum.ORDER_PAY_ROUTING_KEY.code);
        message.setQueueNme(RabbitMqQueueEnum.ORDER_PAY_QUEUE.code);
        message.setMessageId(orderPay.getPaySn());
        message.setRetryNum(0);
        message.setBody(JSON.toJSONString(orderPay));
        message.setMessageType("ORDER_PAY");
        message.setFiled1(orderPay.getPaySn());
        boolean result = messageService.save(message);
        BusinessAssert.isTrue(result, ExceptionUtil.OrderPayExceptionEnum.ORDER_PAY_FAIL);
        rabbitMessageProvider.sendMessage(RabbitMqExchangeEnum.ORDER_PAY_EXCHANGE, RabbitMqRoutingKeyEnum.ORDER_PAY_ROUTING_KEY, orderPay, null);
    }


    @Test
    public void testDeadQueue() {
        OrderPay orderPay = new OrderPay();
        orderPay.setPaySn("1");
        MessageIdCorrelationData correlationData = new MessageIdCorrelationData();
        correlationData.setId("3");
        rabbitMessageProvider.sendMessage(RabbitMqExchangeEnum.ORDER_PAY_EXCHANGE, RabbitMqRoutingKeyEnum.ORDER_PAY_ROUTING_KEY, orderPay, null);
    }

    @Test
    @Transactional
    public void testProductPrice() {
//        Product product = productService.getById("1");
//        product.setPrice(new BigDecimal(7));
//        productService.updateById(product);
//        OrderPay orderPay = orderPayService.getById("231f0842c0de23e7d1b749c2ff4fa0fe");
//        orderPay.setAmount(new BigDecimal(70));
//        orderPayService.updateById(orderPay);
//        BusinessAssert.isTrue(false, ExceptionUtil.ProductExceptionEnum.PRODUCT_NUM_NOT_NULL);
//        QueryWrapper queryWrapper = new QueryWrapper();
//        List<ZuulApiRoute> list = service.list(queryWrapper);
    }


    @Test
    public void testBigDecimal() {
//        List<Order> list = new ArrayList<>();
//        Order order = new Order();
//        order.setAmount(new BigDecimal(1.2));
//        Order order1 = new Order();
//        order1.setAmount(new BigDecimal(2.5));
//        list.add(order);
//        list.add(order1);
        List<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal(1));
        list.add(new BigDecimal(2));
        BigDecimal amount = list.stream().collect(CollectorsUtil.summingBigDecimal(x -> x));
        System.out.println(amount);
    }


}
