package com.ruan.yuanyuan.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券实体类
 */
@TableName("rf_coupon")
public class CouponEntity implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 流量券所属的用户的账号id
     */
    @TableField("user_account_id")
    private Long userAccountId;
    /**
     * 流量券金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;
    /**
     * 流量券的状态
     */
    private Integer status;
    /**
     * 流量券的开始生效时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 流量券的终止过期时间
     */
    @TableField("end_time")
    private Date endTime;
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
    public Long getUserAccountId() {
        return userAccountId;
    }
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }
    public BigDecimal getCouponAmount() {
        return couponAmount;
    }
    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
