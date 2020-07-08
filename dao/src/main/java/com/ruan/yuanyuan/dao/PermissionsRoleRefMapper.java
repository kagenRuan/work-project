package com.ruan.yuanyuan.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.entity.PermissionsRoleRef;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:32
 * version:
 * Description: 资源角色表
 */
public interface PermissionsRoleRefMapper extends BaseMapper<PermissionsRoleRef> {


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
