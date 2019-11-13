package com.ruan.yuanyuan.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-24
 * Time: 19:03
 * version: 1.0.0
 * Description: 商品控制器
 */
@Controller
@RequestMapping("/mall/product")
public class ProductController {

    /**
     * 商品跳转页面
     * @return
     */
    @RequestMapping("/index")
    @RequiresPermissions("product:index")
    public String index(){
        return "/product/index";
    }
}
