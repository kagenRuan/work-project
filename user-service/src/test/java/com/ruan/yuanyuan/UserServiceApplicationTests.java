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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
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
public class UserServiceApplicationTests {

    @Autowired
    private IUserAccountHistoryService userAccountHistoryService;

    @Test
    public void get(){
    }


}
