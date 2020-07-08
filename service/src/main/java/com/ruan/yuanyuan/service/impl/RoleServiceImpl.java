package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.RoleMapper;
import com.ruan.yuanyuan.dto.RoleDto;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.service.IPermissionsRoleService;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.service.IRoleService;
import com.ruan.yuanyuan.vo.RoleVo;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据用户ID查询用户角色
     * @param userId
     * @return Set<Role>
     */
    @Override
    public List<RoleVo> findRoleByUserId(String userId) {
        List<RoleVo> roleVos = roleMapper.findRoleByUserId(userId);
        return roleVos;
    }
}
