package com.ruan.yuanyuan.vo;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-29
 * Time: 14:03
 * version:1.0
 * Description:用户VO
 */
public class UserVo {
    /**
     * 用户ID
     */
    private String id;
    /**
     * 用户姓名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * sessionId
     */
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
