package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.PermissionsMapper;
import com.ruan.yuanyuan.dto.PermissionsDto;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:52
 * version:
 * Description: 资源 添加，删除和修改了权限后都需要清空缓存
 */
@Service
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper,Permissions> implements IPermissionsService {


    @Autowired
    private PermissionsMapper permissionsMapper;

    /**
     * 根据ID查询资源
     * @param id
     * @return
     */
    @Override
    public Permissions findPermissionsById(String id) {
        Permissions permissions = permissionsMapper.findById(id);
        return permissions;
    }

    /**
     * 查询所有的角色
     * @return
     */
    @Override
    public List<Permissions> findAll() {
        List<Permissions> permissionsList = permissionsMapper.findAll();
        return permissionsList;
    }

    /**
     * 根据ID删除资源
     * @param id
     */
    @Override
    @Transactional
    public void deletePermissionsById(String id) {
        permissionsMapper.deletePermissionsById(id);
    }

    /**
     * 添加资源
     * @param permissionsDto
     */
    @Override
    @Transactional
    public void add(PermissionsDto permissionsDto) {
        permissionsMapper.add(permissionsDto);
    }

    /**
     * 根据parentId查询资源
     * @param parentId 父id
     * @return
     */
    @Override
    public List<Permissions> findAllPermissionsByParentId(String parentId) {
        List<Permissions> permissions = permissionsMapper.findAllPermissionsByParentId(parentId);
        return permissions;
    }

    @Override
    public List<PermissionsVo> findPermissionsByUserId(String userId) {
        List<PermissionsVo> resultPermissionsVoList = new ArrayList<>();
        List<PermissionsVo> permissionsVoList = permissionsMapper.findPermissionsByUserId(userId);
        if(!ObjectUtils.isEmpty(permissionsVoList)){
            permissionsVoList.forEach(obj ->{
                PermissionsVo permissionsVo = new PermissionsVo();
                if("0".equals(obj.getParentId())){
                    permissionsVo.setId(obj.getId());
                    permissionsVo.setName(obj.getName());
                    permissionsVo.setUrl(obj.getUrl());
                    permissionsVo.setParentId(obj.getParentId());
                    resultPermissionsVoList.add(permissionsVo);
                }
            });
        }
        if(!ObjectUtils.isEmpty(resultPermissionsVoList)){
            Map<String,List<PermissionsVo>> maps = permissionsVoList.stream().
                                                                     filter(obj ->!obj.getParentId().equals("0")).
                                                                     collect(Collectors.groupingBy(PermissionsVo::getParentId));

            for (PermissionsVo permissionsVo : resultPermissionsVoList) {
                List<PermissionsVo> permissionsVos = maps.get(permissionsVo.getId());
                permissionsVo.setChildes(permissionsVos);
            }

        }
        return resultPermissionsVoList;
    }

    /**
     * 根据角色查询权限
     * @param roles
     * @return
     */
    @Override
    public Set<PermissionsVo> findPermissionsByRoleId(Set<Role> roles) {
        return permissionsMapper.findPermissionsByRoleId(roles);
    }
}
