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
     * 查询资源
     *
     * @param id
     * @return
     */
    Permissions findById(String id);

    /**
     * 查询所有的资源
     *
     * @return
     */
    List<Permissions> findAll();

    /**
     * 删除资源
     *
     * @param id
     */
    void deletePermissionsById(String id);

    /**
     * 添加资源
     *
     * @param permissionsDto
     */
    void add(@Param("permissions") PermissionsDto permissionsDto);

    /**
     * 根据父id查询资源
     *
     * @param parentId 父id
     * @return
     */
    List<Permissions> findAllPermissionsByParentId(String parentId);

    /**
     * 根据用户id查询用户的资源
     *
     * @param userId
     * @return
     */
    List<PermissionsVo> findPermissionsByUserId(String userId);

    /**
     * 根据角色查询权限
     *
     * @param roles
     * @return
     */
    Set<PermissionsVo> findPermissionsByRoleId(Set<Role> roles);
}
