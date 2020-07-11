package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruan.yuanyuan.dto.RoleDto;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:32
 * version:
 * Description:
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID查询用户角色
     * @param userId
     * @return
     */
    List<RoleVo> findRoleByUserId(@Param("userId") String userId);

    /**
     * 根据用户ID查询用户角色
     * @param userId
     * @return
     */
    List<RoleVo> findRoleByUserIdPage(Page<RoleVo> page,@Param("userId") String userId);

    /**
     * 根据所有的角色信息并分页
     * @return List<Role>
     */
    List<RoleVo> findAllRolePage(Page<RoleVo> page);

    /**
     * 根据所有的角色信息
     * @return List<Role>
     */
    List<RoleVo> findAllRoleList();
}
