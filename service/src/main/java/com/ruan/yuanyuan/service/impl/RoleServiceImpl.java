package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.RoleMapper;
import com.ruan.yuanyuan.dto.RoleDto;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.service.IPermissionsRoleService;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:52
 * version:
 * Description: 角色
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IPermissionsRoleService permissionsRoleService;
    @Autowired
    private IPermissionsService permissionsService;

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    @Override
    public Set<Role> findRoleById(String id) {
        Set<Role> roles = roleMapper.findRoleById(id);
        return roles;
    }

    /**
     * 查询所有的角色
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        List<Role> roleList = roleMapper.findAll();
        return roleList;
    }

    /**
     * 根据ID删除角色
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteRoleById(String id) {
        roleMapper.deleteRoleById(id);
    }

    /**
     * 添加角色
     *
     * @param roleDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RoleDto roleDto) {
        roleMapper.add(roleDto);
        List<Permissions> permissionsList = permissionsService.findAllPermissionsByParentId(roleDto.getPermissionsId());
        Permissions permissions = permissionsService.findPermissionsById(roleDto.getPermissionsId());
        permissionsList.add(permissions);

        if (!ObjectUtils.isEmpty(permissionsList)) {
            permissionsList.forEach(obj -> {
                // 添加用户与角色关系
                permissionsRoleService.add(obj.getId(), roleDto.getId());
            });
        }
    }
}
