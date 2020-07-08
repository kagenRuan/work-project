package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:22
 * version:
 * Description: 角色资源中间表
 */
@TableName("yy_permissions_role_ref")
public class PermissionsRoleRef extends BaseEntity {
    /**
     * 资源ID
     */
    @TableField("permissions_id")
    private String permissionsId;
    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    public String getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(String permissionsId) {
        this.permissionsId = permissionsId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
