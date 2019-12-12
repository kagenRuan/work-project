package com.ruan.yuanyuan.service;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:50
 * version:
 * Description: 资源角色
 */
public interface IPermissionsRoleService {

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
}
