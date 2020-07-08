package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.UserRoleMapper;
import com.ruan.yuanyuan.entity.UserRoleRef;
import com.ruan.yuanyuan.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:52
 * version:
 * Description: 用户与角色接口服务
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleRef> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    /**
     * 删除用户角色关系
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteUserRoleById(String id) {
        userRoleMapper.deleteUserRoleById(id);
    }

    /**
     * 添加用户与角色关系
     *
     * @param userId 用户ID
     * @param roleId 角色id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(String userId, String roleId) {
        userRoleMapper.addRole(userId, roleId);
    }
}
