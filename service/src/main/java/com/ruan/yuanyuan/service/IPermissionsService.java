package com.ruan.yuanyuan.service;

import com.ruan.yuanyuan.dto.PermissionsDto;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.vo.PermissionsVo;

import java.util.List;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:50
 * version:
 * Description: 资源
 */
public interface IPermissionsService {

    /**
     * 查询资源
     * @param id
     * @return
     */
    Permissions findPermissionsById(String id);

    /**
     * 查询所有的资源
     * @return
     */
    List<Permissions> findAll();

    /**
     * 删除资源
     * @param id
     */
    void deletePermissionsById(String id);

    /**
     * 添加资源
     * @param permissionsDto
     */
    void add(PermissionsDto permissionsDto);

    /**
     * 根据parentId查询权限资源
     * @return
     */
    List<Permissions> findAllPermissionsByParentId(String parentId);

    /**
     *
     * @param userId
     * @return
     */
    List<PermissionsVo> findPermissionsByUserId(String userId);

    /**
     * 根据角色查询权限资源
     * @param roles
     * @return
     */
    Set<PermissionsVo>  findPermissionsByRoleId(Set<Role> roles);
}
