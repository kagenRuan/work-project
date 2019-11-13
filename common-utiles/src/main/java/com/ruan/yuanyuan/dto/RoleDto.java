package com.ruan.yuanyuan.dto;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 17:19
 * version:
 * Description:
 */
public class RoleDto {

    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色类型
     */
    private String code;
    /**
     * 资源ID
     */
    private String permissionsId;


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

    public String getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(String permissionsId) {
        this.permissionsId = permissionsId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
