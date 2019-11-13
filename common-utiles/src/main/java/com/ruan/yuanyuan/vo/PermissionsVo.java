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
    private String name;
    /**
     * 资源url
     */
    private String url;
    /**
     * 父编号,本权限可能是该父编号权限的子权限
     */
    private String parentId;
    /**
     * 父节点id
     */
    private String parentIds;
    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 权限字符串
     */
    private String permission;
    /**
     * 子项
     */
    private List<PermissionsVo> childes;

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

    public List<PermissionsVo> getChildes() {
        return childes;
    }

    public void setChildes(List<PermissionsVo> childes) {
        this.childes = childes;
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

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
