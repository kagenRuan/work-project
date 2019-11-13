package com.ruan.yuanyuan.service;

import com.ruan.yuanyuan.dto.RoleDto;
import com.ruan.yuanyuan.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:50
 * version:
 * Description: 角色
 */
public interface IRoleService {

    /**
     * 查询角色
     * @param id
     * @return
     */
    Set<Role> findRoleById(String id);

    /**
     * 查询所有的角色
     * @return
     */
    List<Role> findAll();

    /**
     * 删除角色
     * @param id
     */
    void deleteRoleById(String id);

    /**
     * 添加角色
     * @param roleDto
     */
    void add(RoleDto roleDto);
}
