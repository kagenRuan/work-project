package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 22:16
 * version:
 * Description: 用户接口
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    User findUserByName(String name);

    /**
     * 查询所有的用户信息
     * @return
     */
    List<User> findAllUser();

    /**
     * 添加用户
     * @param userDto
     */
    void addUser(@Param("user") UserDto userDto);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(String id);
}
