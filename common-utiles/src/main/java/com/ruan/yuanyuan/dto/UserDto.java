package com.ruan.yuanyuan.dto;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 17:57
 * version:
 * Description:
 */
public class UserDto implements Serializable {


    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 主键
     */
    private String id;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 记住我
     */
    private boolean rememberMe = true;

    private BigDecimal price = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return StringUtils.isEmpty(status) ? "0" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
