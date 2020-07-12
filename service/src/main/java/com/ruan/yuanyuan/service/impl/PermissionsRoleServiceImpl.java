package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.PermissionsRoleRefMapper;
import com.ruan.yuanyuan.entity.PermissionsRoleRef;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IPermissionsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:52
 * version:
 * Description: 资源与角色
 */
@Service
@SuppressWarnings("all")
public class PermissionsRoleServiceImpl extends ServiceImpl<PermissionsRoleRefMapper, PermissionsRoleRef> implements IPermissionsRoleService {

    @Autowired
    private PermissionsRoleRefMapper permissionsRoleMapper;

    /**
     * 删除资源与角色
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRealmRoleById(String id) {
        permissionsRoleMapper.deletePermissionsRoleById(id);
    }

    /**
     * 添加资源与角色
     *
     * @param permissionsId
     * @param roleId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(String permissionsId, String roleId) {
        permissionsRoleMapper.add(permissionsId, roleId);
    }


    /**
     * 添加资源与角色信息
     * @param permissionIds 资源信息集合
     * @param roleId 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<String> permissionIds,String roleId) {

        //
        List<PermissionsRoleRef> permissionsRoleRefs = permissionIds.stream().map(obj ->{
            PermissionsRoleRef permissionsRoleRef = new PermissionsRoleRef();
            permissionsRoleRef.initBean();
            permissionsRoleRef.setPermissionsId(obj);
            permissionsRoleRef.setRoleId(roleId);
            return permissionsRoleRef;
        }).collect(Collectors.toList());
        //已分配的权限数据
        List<PermissionsRoleRef> permissionsRoleRefList = baseMapper.selectList(new QueryWrapper<PermissionsRoleRef>().eq("role_id",roleId));
        //根据需要添加的数据和已有的数据进行过滤，过滤掉两个集合中相同的数据
        List<PermissionsRoleRef> finalPermissionsRoleRefList = permissionsRoleRefList;
        List<PermissionsRoleRef> result = permissionsRoleRefs.stream().filter(obj1 -> !obj1.getPermissionsId().equals("0")).filter(obj -> finalPermissionsRoleRefList.stream().noneMatch(obj1 -> obj.getPermissionsId().equals(obj1.getPermissionsId()))).collect(Collectors.toList());
        if(!ObjectUtils.isEmpty(result)){
            saveBatch(result);
        }
        //
        permissionsRoleRefList = permissionsRoleRefList.stream().filter(obj -> permissionsRoleRefs.stream().filter(obj1 -> !obj1.getPermissionsId().equals("0")).noneMatch(obj1 -> obj.getPermissionsId().equals(obj1.getPermissionsId()))).collect(Collectors.toList());
        if(!ObjectUtils.isEmpty(permissionsRoleRefList)){
            baseMapper.deleteBatchIds(permissionsRoleRefList.stream().map(obj -> obj.getId()).collect(Collectors.toList()));
        }


    }

    /**
     * 根据角色ID查询资源ID
     * @param id 角色ID
     * @return
     */
    @Override
    public List<PermissionsRoleRef> findPermissionByRoleId(String roleId) {
        List<PermissionsRoleRef> permissionsRoleRefList = baseMapper.selectList(new QueryWrapper<PermissionsRoleRef>().eq("role_id",roleId));
        return permissionsRoleRefList;
    }

    /**
     * 根据角色ID删除角色与资源信息关联表
     * @param roleId 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleId(String roleId) {
        baseMapper.delete(new QueryWrapper<PermissionsRoleRef>().eq("role_id",roleId));
    }
}
