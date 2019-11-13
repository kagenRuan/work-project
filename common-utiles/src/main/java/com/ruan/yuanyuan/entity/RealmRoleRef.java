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
@TableName("yy_realm_role_ref")
public class RealmRoleRef extends BaseEntity {
    /**
     * 资源ID
     */
    @TableField("realm_id")
    private String realmId;
    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
