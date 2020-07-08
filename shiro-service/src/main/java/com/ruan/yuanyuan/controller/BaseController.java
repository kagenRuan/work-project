package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: BaseController
 * @author: ruanyuanyuan
 * @date: 2020/7/6 10:21
 * @version: 1.0
 * @description: 基础类的控制器
 **/
public class BaseController {

    /**
     * 获取到当前登录用户信息
     * @return
     */
    public User getLoginUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
