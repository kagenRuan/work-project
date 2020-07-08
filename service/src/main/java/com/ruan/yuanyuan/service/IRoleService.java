package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.dto.RoleDto;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.vo.RoleVo;

import java.util.List;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:50
 * version:
 * Description: 角色
 */
public interface IRoleService extends IService<Role> {


    /**
     * 根据用户ID查询用户角色
     * @param userId
     * @return Set<Role>
     */
    List<RoleVo> findRoleByUserId(String userId);
}
