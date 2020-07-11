package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.UserRoleRef;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:50
 * version:
 * Description: 用户角色
 */
public interface IUserRoleService extends IService<UserRoleRef> {

    /**
     * 删除用户与角色
     *
     * @return
     */
    void deleteUserRoleById(String id);

    /**
     * 添加用户与角色关系
     *
     * @param userId 用户ID
     * @param roleId 角色id
     */
    void addRole(String userId, String roleId);

}
