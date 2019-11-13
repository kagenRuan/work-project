package com.ruan.yuanyuan.spring.service;

import com.ruan.yuanyuan.annoation.RService;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-09-29
 * @Time: 15:20
 * @version:1.0
 * @Description:用户服务接口
 */
@RService
public class UserServiceImpl implements UserService{

    @Override
    public void test() {
        System.out.println("hello");
    }
}
