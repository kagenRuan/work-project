package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类
 */
@TableName("rf_order")
public class OrderEntity implements Serializable {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 用户账号id
     */
    @TableField("user_account_id")
    private Long userAccountId;
    /**
     * 商户账号id
     */
    @TableField("business_account_id")
    private Long businessAccountId;
    /**
     * 商户名称
     */
    @TableField("business_name")
    private String businessName;
    /**
     * 支付金额
     */
    private BigDecimal amount;
    /**
     * 订单标题
     */
    private String title;
    /**
     * 订单分类
     */
    private String type;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 支付方式类型
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 充值说明
     */
    @TableField("refill_comment")
    private String refillComment;
    /**
     * 充值手机号码
     */
    @TableField("refill_phone_number")
    private String refillPhoneNumber;
    /**
     * 充值流量
     */
    @TableField("refill_data")
    private Long refillData;
    /**
     * 赠送积分
     */
    private Double credit;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 修改时间
     */
    @TableField("modified_time")
    private Date modifiedTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Long getUserAccountId() {
        return userAccountId;
    }
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }
    public Long getBusinessAccountId() {
        return businessAccountId;
    }
    public void setBusinessAccountId(Long businessAccountId) {
        this.businessAccountId = businessAccountId;
    }
    public String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getPayType() {
        return payType;
    }
    public void setPayType(Integer payType) {
        this.payType = payType;
    }
    public String getRefillComment() {
        return refillComment;
    }
    public void setRefillComment(String refillComment) {
        this.refillComment = refillComment;
    }
    public String getRefillPhoneNumber() {
        return refillPhoneNumber;
    }
    public void setRefillPhoneNumber(String refillPhoneNumber) {
        this.refillPhoneNumber = refillPhoneNumber;
    }
    public Long getRefillData() {
        return refillData;
    }
    public void setRefillData(Long refillData) {
        this.refillData = refillData;
    }
    public Double getCredit() {
        return credit;
    }
    public void setCredit(Double credit) {
        this.credit = credit;
    }
    public Date getCreatedTime() {
        return new Date();
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Date getModifiedTime() {
        return new Date();
    }
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
