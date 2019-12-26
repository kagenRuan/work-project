package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 12:00
 * version:
 * Description: 用户服务控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 修改用户账户金额
     * @param userId 用户ID
     * @param money  用户金额
     * @return ResultObject
     */
    @RequestMapping(value = "/updateMoneyById",method = RequestMethod.GET)
    public ResultObject updateMoneyById(String userId, BigDecimal money){
        return userService.updateMoneyById(userId, money);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public ResultObject addUser(@RequestBody User user){
        if(null != null){
            user.initBean();
        }
        return userService.addUser(user);
    }

}
