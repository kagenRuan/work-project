package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.dto.RoleDto;
import com.ruan.yuanyuan.entity.Role;
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
     * 查询角色
     * @param id
     * @return
     */
    Set<Role> findRoleById(String id);

    /**
     * 查询所有的角色
     * @return
     */
    List<Role> findAll();

    /**
     * 删除角色
     * @param id
     */
    void deleteRoleById(String id);

    /**
     * 添加角色
     * @param roleDto
     */
    void add(@Param("role") RoleDto roleDto);
}
