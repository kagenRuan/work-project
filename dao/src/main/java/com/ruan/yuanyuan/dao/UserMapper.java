package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.vo.UserVo;
import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 22:16
 * version:
 * Description: 用户接口
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询所有用户名
     * @return
     */
    List<UserVo> findAll(Page<UserVo> page);
}
