package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.entity.UserRoleRef;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:33
 * version:
 * Description:
 */
public interface UserRoleMapper extends BaseMapper<UserRoleRef> {

    /**
     * 删除用户与角色
     * @return
     */
    void deleteUserRoleById(String id);

    /**
     * 添加用户与角色关系
     * @param userId
     * @param roleId
     */
    void add(String userId, String roleId);
}
