package com.ruan.yuanyuan.config;

import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @ClassName: TestCorrelationData
 * @author: ruanyuanyuan
 * @date: 2020/7/23 16:47
 * @version: 1.0
 * @description:
 **/
public class TestCorrelationData extends CorrelationData {

    @Override
    public String getId() {
        return super.getId();
    }

}
