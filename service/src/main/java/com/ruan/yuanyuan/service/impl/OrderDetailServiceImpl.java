package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.OrderDetailMapper;
import com.ruan.yuanyuan.entity.OrderDetail;
import com.ruan.yuanyuan.service.IOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 09:10
 * version:1.0
 * Description:订单详情服务
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
