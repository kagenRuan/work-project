package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.UserMapper;
import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.entity.UserRoleRef;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IUserRoleService;
import com.ruan.yuanyuan.service.IUserService;
import com.ruan.yuanyuan.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 22:24
 * version:
 * Description: 用户信息服务接口
 */

@Service
@SuppressWarnings("all")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 查询所有的用户
     * @return List<UserVo>
     */
    @Override
    public List<UserVo> findAll(Page<UserVo> page) {
        return userMapper.findAll(page);
    }

    /**
     * 添加用户
     * @param userDto 用户参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.initBean();
        baseMapper.insert(user);
        if(!StringUtils.isBlank(userDto.getRoleId())){
            //保存用户角色表信息
            UserRoleRef roleRef = new UserRoleRef();
            roleRef.setRoleId(userDto.getRoleId());
            roleRef.setUserId(user.getId());
            roleRef.initBean();
            userRoleService.save(roleRef);
        }
    }

    /**
     * 删除用户
     * @param userId 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> userId) {
        List<User> users = baseMapper.selectBatchIds(userId);
        BusinessAssert.notNull(users,ExceptionUtil.UserExceptionEnum.USER_NOT_EXISTENT);
        baseMapper.deleteBatchIds(userId);
    }


    /**
     * 修改用户
     * @param userDto 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserDto userDto) {
        User user = baseMapper.selectById(userDto.getId());
        BusinessAssert.notNull(user,ExceptionUtil.UserExceptionEnum.USER_NOT_EXISTENT);
        BeanUtils.copyProperties(userDto,user);
        user.updateBean();
        baseMapper.updateById(user);
    }


//====================TCC=============
//    /**
//     * 修改用户账户金额
//     * @param userId 用户ID
//     * @param money 金额
//     * @param transactionContext TCC 事务上下文
//     * @return User
//     * TODO TCC try方法表示尝试往账户中加钱，但是并不是真正的加钱，只是往用户账户历史表中【UserAccountHistory】增加一条记录
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    @Compensable(confirmMethod = "confirmUpdateMoneyById",cancelMethod = "cancelUpdateMoneyById")
//    public ResultObject updateMoneyById(TransactionContext transactionContext,String userId, BigDecimal money,String paySn) {
//        logger.info("<<<<<<<<<<<<<<<UserServiceImpl#updateMoneyById方法 进入到TCC分布式事务try方法中 START");
//
//        //判断用户是否存在
//        User user = this.baseMapper.selectById(userId);
//        BusinessAssert.notNull(user, ExceptionUtil.BuyerExceptionEnum.BUYER_FIND_FAIL);
//        //查询用户账户历史记录
//        UserAccountHistory userAccountHistory = userAccountHistoryService.getOne(new QueryWrapper<UserAccountHistory>().eq("user_id",userId).eq("pay_sn",paySn));
//        if(null == userAccountHistory){
//            userAccountHistory = new UserAccountHistory();
//            userAccountHistory.setMoney(money);
//            userAccountHistory.setPaySn(paySn);
//            userAccountHistory.setUserId(userId);
//            userAccountHistory.setStatus(UserAccountHistoryStatusEnum.TRYING.getCode());
//            userAccountHistory.initBean();
//            userAccountHistoryService.save(userAccountHistory);
//        }else if(userAccountHistory.getStatus().equals(UserAccountHistoryStatusEnum.CANCEL.getCode())){
//            userAccountHistory.setStatus(UserAccountHistoryStatusEnum.TRYING.getCode());
//            userAccountHistory.updateBean();
//            userAccountHistoryService.updateById(userAccountHistory);
//        }
//        logger.info("<<<<<<<<<<<<<<<UserServiceImpl#updateMoneyById方法 进入到TCC分布式事务try方法中 END");
//        return new ResultObject();
//    }
//
//    /**
//     * TCC confirm确认方法
//     * @param transactionContext TCC 事务上下文
//     * @param userId 卖家ID
//     * @param money 金额
//     * @return ResultObject
//     * TODO 如果try方法已经成功，那么在confirm方法中查询用户账户历史并做幂等性判断
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public ResultObject confirmUpdateMoneyById(TransactionContext transactionContext,String userId, BigDecimal money,String paySn) {
//
//        logger.info("<<<<<<<<<<<<<<<UserServiceImpl#confirmUpdateMoneyById方法 进入到TCC分布式事务confirm方法中 START");
//
//        //查询用户账户历史记录
//        UserAccountHistory userAccountHistory = userAccountHistoryService.getOne(new QueryWrapper<UserAccountHistory>().
//                eq("user_id",userId).
//                eq("pay_sn",paySn));
//
//        //幂等性判断
//        if(null == userAccountHistory || !UserAccountHistoryStatusEnum.TRYING.getCode().equals(userAccountHistory.getStatus())){
//            return null;
//        }
//        userAccountHistory.setStatus(UserAccountHistoryStatusEnum.CONFIRM.getCode());
//        userAccountHistoryService.updateById(userAccountHistory);
//
//        //开始对用户账户金额加款
//        ResultObject resultObject = new ResultObject();
//        User user = this.baseMapper.selectById(userId);
//        BusinessAssert.notNull(user, ExceptionUtil.BuyerExceptionEnum.BUYER_FIND_FAIL);
//        user.updateBean();
//        //修改用户
//        boolean result = this.updateById(user);
//        resultObject.setCode(ResultEnum.getResultEnum(result).getCode());
//        resultObject.setMsg(ResultEnum.getResultEnum(result).getMsg());
//        logger.info("<<<<<<<<<<<<<<<UserServiceImpl#confirmUpdateMoneyById方法 进入到TCC分布式事务confirm方法中 END");
//        return resultObject;
//    }
//
//    /**
//     * TCC 取消方法
//     * @param transactionContext TCC 事务上下文
//     * @param userId 卖家ID
//     * @param money 金额
//     * @return ResultObject
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public ResultObject cancelUpdateMoneyById(TransactionContext transactionContext,String userId, BigDecimal money,String paySn) {
//        logger.info("<<<<<<<<<<<<<<<UserServiceImpl#cancelUpdateMoneyById方法 进入到TCC分布式事务cancel方法中 START");
//        ResultObject resultObject = new ResultObject();
//        //查询用户账户历史记录
//        UserAccountHistory userAccountHistory = userAccountHistoryService.getOne(new QueryWrapper<UserAccountHistory>().
//                eq("user_id",userId).
//                eq("pay_sn",paySn));
//        //幂等性判断
//        if(null == userAccountHistory || !UserAccountHistoryStatusEnum.TRYING.getCode().equals(userAccountHistory.getStatus())){
//            return null;
//        }
//        userAccountHistory.setStatus(UserAccountHistoryStatusEnum.CANCEL.getCode());
//        userAccountHistory.updateBean();
//        boolean result = userAccountHistoryService.updateById(userAccountHistory);
//        resultObject.setCode(ResultEnum.getResultEnum(result).getCode());
//        resultObject.setMsg(ResultEnum.getResultEnum(result).getMsg());
//        logger.info("<<<<<<<<<<<<<<<UserServiceImpl#cancelUpdateMoneyById方法 进入到TCC分布式事务cancel方法中 START");
//        return resultObject;
//    }





}
