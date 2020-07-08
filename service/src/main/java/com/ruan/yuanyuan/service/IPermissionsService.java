package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.vo.PermissionsVo;

import java.util.List;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:50
 * version:
 * Description: 资源
 */
public interface IPermissionsService extends IService<Permissions> {

    /**
     * 根据角色查询权限资源
     * @param roleIds 角色ID
     * @return Set<PermissionsVo>
     */
    Set<PermissionsVo> findPermissionsByRoleId(Set<String> roleIds);

    /**
     * 根据用户ID查询资源信息
     * @param userId 用户ID
     * @param isButton 是否是菜单 0为按钮1为菜单
     * @return List<PermissionsVo>
     */
    List<PermissionsVo> findPermissionsByUserId(String userId,String isButton);
}
