package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.UserMapper;
import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.service.IUserRoleService;
import com.ruan.yuanyuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 22:24
 * version:
 * Description: 用户信息服务接口
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public User findUserByName(String name) {
        User user = userMapper.findUserByName(name);
        return user;
    }

    /**
     * 查询所有的用户信息
     *
     * @return
     */
    @Override
    public List<User> findAllUser() {
        List<User> userList = userMapper.findAllUser();
        return userList;
    }

    /**
     * 添加用户
     *
     * @param userDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserDto userDto) {
        UserDto userDto1 = null;
        for (int i = 1; i < 2000000; i++) {
            userDto1 = new UserDto();
            userDto1.setUsername(i + "");
            userDto1.setPassword(i + "");
            userDto1.setStatus("0");
            userMapper.addUser(userDto1);
        }
//        //添加用户与角色关系
//        userMapper.addUser(userDto);
//        userRoleService.add(userDto.getId(),userDto.getRoleId());

    }

    /**
     * 删除用户
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteUser(String id) {
        userMapper.deleteUser(id);
    }
}
