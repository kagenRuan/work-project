package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.enums.ResultEnum;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IUserRoleService;
import com.ruan.yuanyuan.service.IUserService;
import org.mengyun.tcctransaction.api.TransactionContext;
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
    @Autowired
    private IUserRoleService userRoleService;


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResultObject test(){
        return new ResultObject();
    }

    /**
     * 修改用户账户金额
     * @param userId 用户ID
     * @param money  用户金额
     * @return ResultObject
     */
    @RequestMapping(value = "/updateMoneyById",method = RequestMethod.GET)
    public ResultObject updateMoneyById(@RequestParam("userId") String userId,
                                        @RequestParam("money") BigDecimal money,
                                        @RequestParam("paySn") String paySn){
        return null;
    }

}
