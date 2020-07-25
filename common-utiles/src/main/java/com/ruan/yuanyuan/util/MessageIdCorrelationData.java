package com.ruan.yuanyuan.util;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 12:23
 * version:
 * Description: 消息唯一ID
 */
public class MessageIdCorrelationData  extends CorrelationData {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
