package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-27
 * Time: 14:25
 * version:1.0
 * Description:买家实体
 */
@TableName("yy_buyer")
public class Buyer extends BaseEntity {
    /**
     * 买家名称
     */
    private String name;
    /**
     * 买家账户余额
     */
    private BigDecimal amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
