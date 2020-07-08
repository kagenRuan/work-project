package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:22
 * version:
 * Description: 资源表
 */
@TableName("yy_permissions")
public class Permissions extends BaseEntity {
    /**
     * 资源名称
     */
    @TableField("name")
    private String name;
    /**
     * 资源请求地址
     */
    @TableField("url")
    private String url;
    /**
     * 父级ID
     */
    @TableField("parent_id")
    private String parentId;
    /**
     * 是否是按钮 0按钮 1菜单
     */
    @TableField("is_button")
    private String isButton;
    /**
     * 权限标识：主要用于增删查改这些动作
     */
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsButton() {
        return isButton;
    }

    public void setIsButton(String isButton) {
        this.isButton = isButton;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
