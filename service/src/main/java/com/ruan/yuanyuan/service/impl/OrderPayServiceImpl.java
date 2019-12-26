package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.OrderPayMapper;
import com.ruan.yuanyuan.dto.OrderPayDto;
import com.ruan.yuanyuan.entity.OrderPay;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.enums.ResultEnum;
import com.ruan.yuanyuan.enums.Yum;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.feign.OrderServiceFeign;
import com.ruan.yuanyuan.feign.UserServiceFeign;
import com.ruan.yuanyuan.service.IOrderPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: OrderPayServiceImpl
 * @author: ruanyuanyuan
 * @date: 2019/12/26 14:44
 * @version: 1.0
 * @description:
 **/
@Service
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OrderPay> implements IOrderPayService {

    @Autowired
    private UserServiceFeign userServiceFeign;
    @Autowired
    private OrderServiceFeign orderServiceFeign;

    /**
     * 创建支付订单
     * @param orderId
     * @return
     */
    @Override
    public OrderPay create(List<String> orderId) {
        return null;
    }

    /**
     * 测试方法
     * @return
     */
    @Override
    public ResultObject test() {
        return userServiceFeign.updateMoneyById("", BigDecimal.ZERO);
    }

    /**
     * 支付成功后回调
     * @param payDto
     * @return ResultObject
     * TODO 这里需要使用TCC 分布式事务，当修改订单状态失败或者修改支付订单失败则需要对事务进行回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultObject callBack(OrderPayDto payDto) {
        //第一步 修改订单状态
        ResultObject orderResult = orderServiceFeign.update(payDto.getOrderId());
        if(orderResult.getCode() > 0){
            return orderResult;
        }

        //第二步 修改支付订单状态
        OrderPay orderPay = this.baseMapper.selectOne(new QueryWrapper<OrderPay>().eq("pay_sn",payDto.getPaySn()));
        BusinessAssert.notNull(orderPay, ExceptionUtil.OrderPayExceptionEnum.ORDER_PAY_NOT_FOUND);
        orderPay.setStatus(Yum.YES.getCode());
        orderPay.setUpdateTime(new Date());
        //修改订单
        boolean result = this.updateById(orderPay);
        if(!result){
            return new ResultObject(ResultEnum.FAIL.getCode(),ResultEnum.FAIL.getMsg());
        }

        //第三步 对用户账户金额增减
        ResultObject userResult =  userServiceFeign.updateMoneyById(payDto.getBuyerId(),payDto.getTotalAmount());
        if(userResult.getCode() > 0){
            return userResult;
        }
        return new ResultObject();
    }
}
