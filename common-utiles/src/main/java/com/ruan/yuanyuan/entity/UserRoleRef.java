package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:20
 * version:
 * Description: 用户与角色关系表
 */
@TableName("yy_user_role_ref")
public class UserRoleRef extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;
    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
