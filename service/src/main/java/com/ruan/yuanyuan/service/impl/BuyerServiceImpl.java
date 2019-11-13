package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.BuyerMapper;
import com.ruan.yuanyuan.entity.Buyer;
import com.ruan.yuanyuan.service.IBuyerService;
import org.springframework.stereotype.Service;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-27
 * Time: 14:32
 * version:1.0
 * Description:买家服务接口
 */
@Service
public class BuyerServiceImpl extends ServiceImpl<BuyerMapper, Buyer> implements IBuyerService {
}
