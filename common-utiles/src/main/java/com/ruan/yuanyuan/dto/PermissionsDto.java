package com.ruan.yuanyuan.dto;

import org.springframework.util.StringUtils;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 17:13
 * version:
 * Description:
 */
public class PermissionsDto {
    /**
     * 资源id
     */
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
     * 节点id
     */
    private String parentId;
    /**
     * 是否是菜单和按钮
     */
    private String resourceType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return StringUtils.isEmpty(id) ? "0" : id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
