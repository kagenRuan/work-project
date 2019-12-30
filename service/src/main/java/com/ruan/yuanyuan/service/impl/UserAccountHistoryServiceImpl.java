package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.UserAccountHistoryMapper;
import com.ruan.yuanyuan.entity.UserAccountHistory;
import com.ruan.yuanyuan.service.IUserAccountHistoryService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserAccountHistoryServiceImpl
 * @author: ruanyuanyuan
 * @date: 2019/12/27 15:32
 * @version: 1.0
 * @description: 用户账户历史记录表，主要用于TCC幂等性
 **/
@Service
public class UserAccountHistoryServiceImpl extends ServiceImpl<UserAccountHistoryMapper, UserAccountHistory> implements IUserAccountHistoryService {


}
