package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:19
 * version:
 * Description: 角色表
 */
@TableName("yy_role")
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;
    /**
     * 角色code
     */
    @TableField("code")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
