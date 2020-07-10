package com.ruan.yuanyuan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-25
 * Time: 15:53
 * version:
 * Description:
 */
public class PermissionsVo implements Serializable {

    private String id;
    /**
     * 资源名称
     */
    private String title;
    /**
     * 资源图片
     */
    private String image;
    /**
     * 图片
     */
    private String icon;
    /**
     * 资源url
     */
    private String href;
    /**
     * 父编号,本权限可能是该父编号权限的子权限
     */
    private String parentId;
    /**
     * 权限字符串
     */
    private String permission;
    /**
     * 是否是按钮 0为按钮1位菜单
     */
    private String isButton;

    private String target;
    /**
     * 资源与角色中间表的ID
     */
    private String permissionsRoleId;
    /**
     * 子项
     */
    private List<PermissionsVo> child;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<PermissionsVo> getChild() {
        return child;
    }

    public void setChild(List<PermissionsVo> child) {
        this.child = child;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIsButton() {
        return isButton;
    }

    public void setIsButton(String isButton) {
        this.isButton = isButton;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPermissionsRoleId() {
        return permissionsRoleId;
    }

    public void setPermissionsRoleId(String permissionsRoleId) {
        this.permissionsRoleId = permissionsRoleId;
    }
}
