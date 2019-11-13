package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.PermissionsRoleMapper;
import com.ruan.yuanyuan.entity.RealmRoleRef;
import com.ruan.yuanyuan.service.IPermissionsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:52
 * version:
 * Description: 资源与角色
 */
@Service
public class PermissionsRoleServiceImpl extends ServiceImpl<PermissionsRoleMapper, RealmRoleRef> implements IPermissionsRoleService {

    @Autowired
    private PermissionsRoleMapper permissionsRoleMapper;

    /**
     * 删除资源与角色
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRealmRoleById(String id) {
        permissionsRoleMapper.deletePermissionsRoleById(id);
    }

    /**
     * 添加资源与角色
     * @param permissionsId
     * @param roleId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(String permissionsId, String roleId) {
        permissionsRoleMapper.add(permissionsId,roleId);
    }
}
