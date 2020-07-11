package com.ruan.yuanyuan.vo;

/**
 * @ClassName: RoleVo
 * @author: ruanyuanyuan
 * @date: 2020/7/5 16:57
 * @version: 1.0
 * @description:
 **/
public class RoleVo {
    /**
     * 角色ID
     */
    private String id;
    /**
     * 角色name
     */
    private String name;
    /**
     * 角色CODE
     */
    private String code;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 资源id
     */
    private String permissionId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
