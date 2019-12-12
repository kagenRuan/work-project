package com.ruan.yuanyuan;

import com.alibaba.fastjson.JSON;
import com.ruan.yuanyuan.entity.RabbitMessage;
import com.ruan.yuanyuan.mesage.enums.RabbitMqExchangeEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqQueueEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqRoutingKeyEnum;
import com.ruan.yuanyuan.rabbit.config.RabbitMqConfig;
import com.ruan.yuanyuan.service.IMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMessageServiceApplicationTests {


    @Autowired
    private IMessageService messageService;

    @Test
    public void test() throws SQLException {
        List<RabbitMessage> rabbitMessageList = new ArrayList<>();
        for (int i = 101320; i < 1000000; i++) {
            RabbitMessage message = new RabbitMessage();
            message.setStatus("1");
            message.setExchange(RabbitMqExchangeEnum.ORDER_PAY_EXCHANGE.code);
            message.setRoutingKey(RabbitMqRoutingKeyEnum.ORDER_PAY_ROUTING_KEY.code);
            message.setQueueNme(RabbitMqQueueEnum.ORDER_PAY_QUEUE.code);
            message.setMessageId(i + "");
            message.setRetryNum(i);
            message.setBody(i + "");
            message.setMessageType("OTHER");
            rabbitMessageList.add(message);
            if (rabbitMessageList.size() > 10000) {
                messageService.saveBatch(rabbitMessageList);
                rabbitMessageList.clear();
            }
        }

    }


    @Test
    public void testAnnotationConfig() {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(RabbitMqConfig.class);
    }

}
