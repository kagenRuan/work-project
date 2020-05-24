package com.ruan.yuanyuan.mybatis.mapper;

import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 18:44
 * version:
 * Description:
 */
public class User{
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户状态
     */
    private String status;

    /**
     * 用户金额
     */
    private BigDecimal money;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
