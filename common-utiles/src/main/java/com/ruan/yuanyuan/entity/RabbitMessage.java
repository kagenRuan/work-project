package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ryy
 * @Title: 消息数据库
 * @Description: 消息数据库
 * @date 2018/12/119:52
 */
@TableName("yy_message")
public class RabbitMessage extends BaseEntity{

    private static final long serialVersionUID = -8982270966374304312L;

    /**
     * rabbitmq exchange
     */
    @TableField("exchange")
    private String exchange;
    /**
     * rabbitmq 路由key
     */
    @TableField("routing_key")
    private String routingKey;
    /**
     * 队列名称
     */
    @TableField("queue_name")
    private String queueNme;
    /**
     * 消息状态1、投递失败2、已投递3、消费成功
     */
    @TableField("status")
    private String status;
    /**
     * 消息内容
     */
    @TableField("body")
    private String body;
    /**
     * 消息ID
     */
    @TableField("message_id")
    private String messageId;
    /**
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;
    /**
     * 重试次数
     */
    @TableField("retry_num")
    private Integer retryNum;
    /**
     * 消息类型
     */
    @TableField("message_type")
    private String messageType;
    /**
     * 冗余字段
     */
    private String filed1;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(Integer retryNum) {
        this.retryNum = retryNum;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getFiled1() {
        return filed1;
    }

    public void setFiled1(String filed1) {
        this.filed1 = filed1;
    }

    public String getQueueNme() {
        return queueNme;
    }

    public void setQueueNme(String queueNme) {
        this.queueNme = queueNme;
    }


    public Integer addRetryNum(){
        return getRetryNum()+1;
    }
}
