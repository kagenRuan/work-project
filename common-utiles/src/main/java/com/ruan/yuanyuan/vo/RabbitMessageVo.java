package com.ruan.yuanyuan.vo;

import java.util.Date;

/**
 * @author ryy
 * @Title: 消息数据库
 * @Description: 消息数据库
 * @date 2018/12/119:52
 */
public class RabbitMessageVo {

    private static final long serialVersionUID = -8982270966374304312L;
    private String id;
    /**
     * rabbitmq exchange
     */
    private String exchange;
    /**
     * rabbitmq 路由key
     */
    private String routingKey;
    /**
     * 队列名称
     */
    private String queueNme;
    /**
     * 消息状态1、投递失败2、已投递3、消费成功
     */
    private String status;
    /**
     * 消息内容
     */
    private String body;
    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 重试次数
     */
    private Integer retryNum;
    /**
     * 消息类型
     */
    private String messageType;

    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getQueueNme() {
        return queueNme;
    }

    public void setQueueNme(String queueNme) {
        this.queueNme = queueNme;
    }


    public Integer addRetryNum() {
        return getRetryNum() + 1;
    }
}
