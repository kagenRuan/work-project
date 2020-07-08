package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UnauthorizedController
 * @author: ruanyuanyuan
 * @date: 2020/7/7 15:02
 * @version: 1.0
 * @description:
 **/
@RestController
@RequestMapping("/unauthorized")
public class UnauthorizedController {

    /**
     * 未授权方法
     */
    @RequestMapping("/return401")
    public ResultObject return401(){
        return new ResultObject(ExceptionUtil.SystemExceptionEnum.UNAUTHORIZED.getCode(),ExceptionUtil.SystemExceptionEnum.UNAUTHORIZED.getMessage());
    }
}
