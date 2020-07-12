package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.PermissionsRoleRef;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:50
 * version:
 * Description: 资源角色
 */
public interface IPermissionsRoleService  extends IService<PermissionsRoleRef> {

    /**
     * 删除资源角色信息
     *
     * @param id
     */
    void deleteRealmRoleById(String id);

    /**
     * 添加资源与角色关系
     *
     * @param permissionsId
     * @param roleId
     */
    void add(String permissionsId, String roleId);

    /**
     * 批量保存资源与角色关系数据
     * @param permissionIds 资源信息集合
     * @param roleId 角色ID
     */
    void add(List<String> permissionIds,String roleId);

    /**
     * 根据角色ID查询资源ID
     * @param roleId 角色ID
     * @return
     */
    List<PermissionsRoleRef> findPermissionByRoleId(String roleId);

    /**
     * 根据角色ID删除角色与资源信息关联表
     * @param roleId 角色ID
     */
    void deleteByRoleId(String roleId);
}
