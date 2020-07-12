package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.springframework.web.bind.annotation.RequestBody;

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
    Set<PermissionsVo> findPermissionsByRoleId(Set<String> roleIds,String isButton);

    /**
     * 根据用户ID查询资源信息
     * @param userId 用户ID
     * @param isButton 是否是菜单 0为按钮1为菜单
     * @return List<PermissionsVo>
     */
    List<PermissionsVo> findPermissionsByUserId(String userId,String isButton);

    /**
     * 查询索引的资源信息
     * @return List<PermissionsVo>
     */
    List<PermissionsVo> findAll(String isButton);

    /**
     * 根据资源ID删除资源信息
     * @param id 资源ID
     */
    void deleteById(String id);

    /**
     * 根据资源ID修改资源信息
     * @param permissionsVo 资源信息参数
     */
    void updateById(PermissionsVo permissionsVo);

    /**
     * 添加资源信息
     * @param permissionsVo 资源信息参数
     */
    void add(PermissionsVo permissionsVo);
}
