package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.dto.PermissionsDto;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:32
 * version:
 * Description:资源
 */
public interface PermissionsMapper extends BaseMapper<Permissions> {

    /**
     * 根据角色查询权限
     * @param roleIds 角色ID
     * @return Set<PermissionsVo>
     */
    Set<PermissionsVo> findPermissionsByRoleId(@Param("roleIds") Set<String> roleIds,@Param("isButton") String isButton);
    /**
     * 根据用户ID查询资源信息
     * @param userId 用户ID
     * @param isButton 是否是菜单 0为按钮 1为菜单
     * @return List<PermissionsVo>
     */
    List<PermissionsVo> findPermissionsByUserId(@Param("userId") String userId, @Param("isButton") String isButton);

    /**
     * 查询所有的资源信息
     * @return
     */
    List<PermissionsVo> findAll(@Param("isButton") String isButton);
}
