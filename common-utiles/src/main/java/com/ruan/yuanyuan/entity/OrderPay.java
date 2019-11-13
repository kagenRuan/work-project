package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 09:55
 * version:1.0
 * Description:支付订单
 */
@TableName("yy_order_pay")
public class OrderPay extends BaseEntity {


    /**
     * 第三方支付编号
     */
    @TableField("three_sn")
    private String threeSn;
    /**
     * 支付类型：支付宝，微信，银行
     */
    @TableField("pay_type")
    private String payType;
    /**
     * 支付金额
     */
    private BigDecimal amount;
    /**
     * 支付状态Y已支付N未支付
     */
    private String status;
    /**
     * 支付单号
     */
    @TableField("pay_sn")
    private String paySn;
    /**
     * 买家ID
     */
    @TableField("buyer_id")
    private String buyerId;

    public String getThreeSn() {
        return threeSn;
    }

    public void setThreeSn(String threeSn) {
        this.threeSn = threeSn;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public String toString() {
        return "OrderPay{" +
                "threeSn='" + threeSn + '\'' +
                ", payType='" + payType + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", paySn='" + paySn + '\'' +
                '}';
    }
}
