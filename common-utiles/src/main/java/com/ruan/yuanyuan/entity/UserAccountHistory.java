package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: UserAccountHistory
 * @author: ruanyuanyuan
 * @date: 2019/12/27 15:22
 * @version: 1.0
 * @description: 用户账户历史表，主要用于做TCC事务幂等性判断
 **/
@TableName("yy_user_account_history")
public class UserAccountHistory extends BaseEntity implements Serializable {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;
    /**
     * 状态
     */
    private String status;

    /**
     * 支付编号
     */
    @TableField("pay_sn")
    private String paySn;
    /**
     * 金额
     */
    private BigDecimal money;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
