package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.RoleMapper;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.PermissionsRoleRef;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.service.IPermissionsRoleService;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.service.IRoleService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import com.ruan.yuanyuan.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:52
 * version:
 * Description: 角色
 */
@Service
@SuppressWarnings("all")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IPermissionsService permissionsService;
    @Autowired
    private IPermissionsRoleService permissionsRoleService;

    /**
     * 根据用户ID查询用户角色
     * @param userId
     * @return List<Role>
     */
    @Override
    public List<RoleVo> findRoleByUserId(String userId) {
        List<RoleVo> roleVos = roleMapper.findRoleByUserId(userId);
        return roleVos;
    }
    /**
     * 根据用户ID查询用户角色并分页
     * @param userId
     * @return List<Role>
     */
    @Override
    public List<RoleVo> findRoleByUserIdPage(Page<RoleVo> page, String userId) {
        List<RoleVo> roleVos = roleMapper.findRoleByUserIdPage(page,userId);
        return roleVos;
    }

    /**
     * 根据所有的角色信息并分页
     * @return List<Role>
     */
    @Override
    public List<RoleVo> findAllRolePage(Page<RoleVo> page) {
        List<RoleVo> roles = roleMapper.findAllRolePage(page);
        return roles;
    }
    /**
     * 根据所有的角色信息
     * @return List<Role>
     */
    @Override
    public List<RoleVo> findAllRoleList() {
        List<RoleVo> roles = roleMapper.findAllRoleList();
        return roles;
    }

    /**
     * 添加角色
     * @param  roleVo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RoleVo roleVo) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVo,role);
        role.initBean();
        baseMapper.insert(role);
    }

    /**
     * 删除角色：这里删除角色后还需要删除角色对应的资源信息
     * @param roleIds 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> roleIds) {
        baseMapper.deleteBatchIds(roleIds);
    }

    /**
     * 修改角色
     * @param  roleVo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleVo roleVo) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVo,role);
        role.initBean();
        baseMapper.updateById(role);
        if(!ObjectUtils.isEmpty(roleVo.getPermissionId())){
            List<PermissionsRoleRef> permissionList = getParentPermisssionId(roleVo.getPermissionId(),roleVo.getId());
            permissionsRoleService.addPermissionRoleList(permissionList);
        }
    }

    /**
     * 获取父级资源信息
     * @param permissionId 资源ID
     */
    public List<PermissionsRoleRef> getParentPermisssionId(String permissionId,String roleId){
        Permissions permissions = permissionsService.getById(permissionId);
        //查询资源信息
        List<PermissionsVo> permissionsVos = permissionsService.findAll();
        //以parentID进行分组
        Map<String,List<PermissionsVo>> map = permissionsVos.stream().collect(Collectors.groupingBy(PermissionsVo::getId));
        //获取需要分配资源的父级资源
        List<PermissionsVo> parentPermission = map.get(permissions.getId());
        List<PermissionsVo> parentPermissionList = new ArrayList<>();
        getPermissionList(parentPermission,map,parentPermissionList);
        parentPermission.addAll(parentPermissionList);
        //获取所有的父级资源ID
        List<PermissionsRoleRef> persmissionIds = Optional.ofNullable(parentPermission.stream().map(obj -> {
            PermissionsRoleRef roleRef = new PermissionsRoleRef();
            roleRef.setRoleId(roleId);
            roleRef.setPermissionsId(obj.getId());
            roleRef.initBean();
            return roleRef;
        }).collect(Collectors.toList())).orElse(new ArrayList<>());
        //对资源进行保存
        return persmissionIds;
    }

    public void getPermissionList(List<PermissionsVo> permissionsVoList,Map<String,List<PermissionsVo>> map,List<PermissionsVo> resultParentPermissionList){
        List<PermissionsVo> foreachPermissionsVoList = new ArrayList<>();
        permissionsVoList.stream().forEach(obj ->{
            String parentId = obj.getParentId();
            //父级菜单不等于0说明还有父级菜单，否则就是顶级菜单就不要循环
            List<PermissionsVo> permissionsVos = map.get(parentId);
            if(!ObjectUtils.isEmpty(permissionsVos)){
                foreachPermissionsVoList.addAll(permissionsVos);
                resultParentPermissionList.addAll(permissionsVos);
            }
        });
        if(!ObjectUtils.isEmpty(foreachPermissionsVoList)){
            getPermissionList(foreachPermissionsVoList,map,resultParentPermissionList);
        }
    }
}
