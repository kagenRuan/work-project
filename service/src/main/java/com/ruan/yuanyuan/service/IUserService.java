package com.ruan.yuanyuan.service;

import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
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
public interface IUserService {
    /**
     * 根据用户名查询用户信息
     *
     * @param name 用户名称
     * @return com.ruam.yuanyuan.entity.User
     */
    User findUserByName(String name);

    /**
     * 查询所有的用户信息
     *
     * @return
     */
    List<User> findAllUser();

    /**
     * 添加用户信息
     *
     * @param userDto
     */
    void addUser(UserDto userDto);

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteUser(String id);

    /**
     * 修改用户账户金额
     * @param userId 用户ID
     * @return User
     */
    ResultObject updateMoneyById(TransactionContext transactionContext, String userId, BigDecimal money,String paySn);

    /**
     * 添加用户
     * @param user
     * @return
     */
    ResultObject addUser(User user);
}
