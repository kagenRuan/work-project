package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: Unauthorized
 * @author: ruanyuanyuan
 * @date: 2020/7/7 15:20
 * @version: 1.0
 * @description:
 **/
@RestController
@RequestMapping("/api/unauthorized")
public class Unauthorized {

    @RequestMapping(value = "/return401",method = RequestMethod.GET)
    public ResultObject return401(){
        return new ResultObject(ExceptionUtil.SystemExceptionEnum.UNAUTHORIZED.getCode(),ExceptionUtil.SystemExceptionEnum.UNAUTHORIZED.getMessage());
    }
}
