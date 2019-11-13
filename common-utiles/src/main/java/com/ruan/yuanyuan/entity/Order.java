package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ryy
 * @Title: 订单实体类
 * @Description: 订单实体类
 * @date 2018/12/119:36
 */
@TableName("yy_order")
public class Order extends BaseEntity {

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;
    /**
     * 订单状态
     */
    @TableField("status")
    private String status;
    /**
     * 订单金额
     */
    private BigDecimal amount;
    /**
     * 店铺ID
     */
    private String shopId;
    /**
     * 订单类型
     */
    @TableField("order_type")
    private String orderType;
    /**
     * 支付单号
     */
    @TableField("pay_sn")
    private String paySn;
    /**
     * 支付状态Y已支付N未支付
     */
    @TableField("pay_status")
    private String payStatus;
    /**
     * 买家ID
     */
    @TableField("buyer_id")
    private String buyerId;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
}
