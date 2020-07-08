package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.PermissionsMapper;
import com.ruan.yuanyuan.dto.PermissionsDto;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:52
 * version:
 * Description: 资源 添加，删除和修改了权限后都需要清空缓存
 */
@Service
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements IPermissionsService {


    @Autowired
    private PermissionsMapper permissionsMapper;

    /**
     * 根据角色查询权限
     * @param roleIds 角色ID
     * @return Set<PermissionsVo>
     */
    @Override
    public Set<PermissionsVo> findPermissionsByRoleId(Set<String> roleIds) {
        return permissionsMapper.findPermissionsByRoleId(roleIds);
    }

    /**
     * 根据用户ID查询资源信息
     * @param userId 用户ID
     * @param isButton 是否是菜单 0为按钮1为菜单
     * @return List<PermissionsVo>
     */
    @Override
    public List<PermissionsVo> findPermissionsByUserId(String userId, String isButton) {
        //验证
        List<PermissionsVo> permissionsVos = permissionsMapper.findPermissionsByUserId(userId, isButton);
        return permissionsVos;
    }

}
