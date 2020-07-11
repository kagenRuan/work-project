package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.vo.UserVo;
import org.mengyun.tcctransaction.api.TransactionContext;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 22:23
 * version:
 * Description: 用户服务接口
 */
public interface IUserService extends IService<User> {

    /**
     * 查询所有的用户
     * @return List<UserVo>
     */
    List<UserVo> findAll(Page<UserVo> page);

    /**
     * 添加用户
     * @param userDto 用户参数
     */
    void add(UserDto userDto);

    /**
     * 删除用户
     * @param userId 用户ID
     */
    void delete(List<String> userId);
    /**
     * 修改用户
     * @param userDto 参数
     */
    void update(UserDto userDto);
}
