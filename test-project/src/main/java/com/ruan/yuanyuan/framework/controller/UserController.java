package com.ruan.yuanyuan.framework.controller;

import com.ruan.yuanyuan.framework.annoation.RAutoWried;
import com.ruan.yuanyuan.framework.annoation.RController;
import com.ruan.yuanyuan.framework.annoation.RRequestMapping;
import com.ruan.yuanyuan.framework.annoation.RRequestParam;
import com.ruan.yuanyuan.framework.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 17:13
 * version:
 * Description:
 */
@RController
@RRequestMapping("/user")
public class UserController {

    @RAutoWried
    private IUserService userService;


    @RRequestMapping("/login")
    public void login(@RRequestParam("name") String name, @RRequestParam("age") String age, HttpServletRequest request, HttpServletResponse response) {

        try {
            response.getWriter().write("获取的参数名称name的值为：" + name + ";age=" + age);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
