package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.entity.UserRoleRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     *
     * @return
     */
    void deleteUserRoleById(String id);

    /**
     * 添加用户与角色关系
     *
     * @param userId
     * @param roleId
     */
    void addRole(@Param("userId") String userId, @Param("roleId") String roleId);
}
