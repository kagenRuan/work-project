package com.ruan.yuanyuan.mesage.vo;



import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-20
 * Time: 15:51
 * version:
 * Description: Order发送消息VO
 */
public class OrderMessageVo implements Serializable {

    /**
     * 订单ID
     */
    private String OrderId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单价格
     */
    private BigDecimal price;
    /**
     * 订单数量
     */
    private Integer orderNum;
    /**
     * 消息ID
     */
    private String messageId;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }


    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "OrderMessageVo{" +
                "orderSn='" + orderSn + '\'' +
                ", price=" + price +
                ", orderNum=" + orderNum +
                '}';
    }
}
