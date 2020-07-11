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


    @Override
    public void addPermissionRoleList(List<PermissionsRoleRef> permissionsRoleRefList) {
        List<String> list = permissionsRoleRefList.stream().map(obj -> obj.getPermissionsId()).collect(Collectors.toList());
        String roleId = permissionsRoleRefList.get(0).getRoleId();
        //已经分配的数据
        List<PermissionsRoleRef> permissionsRoleRefs = baseMapper.selectList(new QueryWrapper<PermissionsRoleRef>().in("permissions_id",list).eq("role_id",roleId));
        permissionsRoleRefList = permissionsRoleRefList.stream().filter(obj -> permissionsRoleRefs.stream().noneMatch(obj1 -> obj.getPermissionsId().equals(obj1.getPermissionsId()))).collect(Collectors.toList());
        super.saveOrUpdateBatch(permissionsRoleRefList);
    }
}
