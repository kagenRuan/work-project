package com.ruan.yuanyuan.mybatis.mapper;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 22:16
 * version:
 * Description: 用户接口
 */
public interface UserMapper{
    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return
     */
    User findUserByName(String name);

}
