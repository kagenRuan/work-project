package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * @return List<Role>
     */
    List<RoleVo> findRoleByUserId(String userId);
    /**
     * 根据用户ID查询用户角色并分页
     * @param userId
     * @return List<Role>
     */
    List<RoleVo> findRoleByUserIdPage(Page<RoleVo> page,String userId);
    /**
     * 查询所有的角色并分页
     * @return List<Role>
     */
    List<RoleVo> findAllRolePage(Page<RoleVo> page);
    /**
     * 查询所有的角色并分页
     * @return List<Role>
     */
    List<RoleVo> findAllRoleList();
    /**
     * 添加角色
     * @param  roleVo 参数
     */
    void add(RoleVo roleVo);
    /**
     * 删除角色
     * @param roleIds 角色ID
     */
    void delete(List<String> roleIds);
    /**
     * 修改角色
     * @param  roleVo 参数
     */
    void update(RoleVo roleVo);
}
