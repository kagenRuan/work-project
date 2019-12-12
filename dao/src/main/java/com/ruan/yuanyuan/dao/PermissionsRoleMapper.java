package com.ruan.yuanyuan.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.entity.RealmRoleRef;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:32
 * version:
 * Description: 资源角色
 */
public interface PermissionsRoleMapper extends BaseMapper<RealmRoleRef> {


    /**
     * 删除资源角色信息
     *
     * @param id
     */
    void deletePermissionsRoleById(String id);

    /**
     * 添加资源与角色关系
     *
     * @param permissionsId
     * @param permissionsId
     */
    void add(String permissionsId, String roleId);
}
