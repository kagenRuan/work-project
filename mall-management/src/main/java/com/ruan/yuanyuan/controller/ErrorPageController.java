package com.ruan.yuanyuan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-16
 * Time: 13:48
 * version:
 * Description: 错误页面跳转控制器
 */
@Controller
@RequestMapping("/error")
public class ErrorPageController {

    /**
     * 400页面
     */
    @RequestMapping("/400")
    public String Page400(){
        return "error/400";
    }

    /**
     * 401页面
     */
    @RequestMapping("/401")
    public String Page401(){
        return "error/401";
    }

    /**
     * 404页面
     */
    @RequestMapping("/404")
    public String Page404(){
        return "error/404";
    }


    /**
     * 500页面
     */
    @RequestMapping("/500")
    public String Page500(){
        return "error/500";
    }


}
