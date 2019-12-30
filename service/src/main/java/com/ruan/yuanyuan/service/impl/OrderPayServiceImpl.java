package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.OrderPayMapper;
import com.ruan.yuanyuan.dto.OrderPayDto;
import com.ruan.yuanyuan.entity.OrderPay;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.enums.PayStatusEnum;
import com.ruan.yuanyuan.enums.ResultEnum;
import com.ruan.yuanyuan.enums.ResultObjectEnum;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.feign.OrderServiceFeign;
import com.ruan.yuanyuan.feign.UserServiceFeign;
import com.ruan.yuanyuan.service.IOrderPayService;
import org.mengyun.tcctransaction.api.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@SuppressWarnings("all")
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OrderPay> implements IOrderPayService {

    private static final Logger logger = LoggerFactory.getLogger(OrderPayServiceImpl.class);

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
        return userServiceFeign.updateMoneyById("11", BigDecimal.ZERO,"111");
    }

    /**
     * TCC try方法
     * 支付成功后回调
     * @param payDto
     * @return ResultObject
     * TODO 这里需要使用TCC 分布式事务，当修改订单状态失败或者修改支付订单失败则需要对事务进行回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Compensable(confirmMethod = "confirmCallBack",cancelMethod = "cancelCallBack")
    public ResultObject callBack(OrderPayDto payDto) {

        logger.info("<<<<<<<<<<<<<<<OrderPayServiceImpl#callBack方法 进入到TCC分布式事务Try方法中 START");
        /**
         * 第一步 修改支付订单状态
         * TODO 在TCC try方法中，不能直接将支付订单状态修改为【支付完成】，try方法的目的就是为了尝试，所以需要将状态修改为【支付中】而不是直接【支付完成】
         */
        OrderPay orderPay = this.baseMapper.selectOne(new QueryWrapper<OrderPay>().eq("pay_sn",payDto.getPaySn()));
        BusinessAssert.notNull(orderPay, ExceptionUtil.OrderPayExceptionEnum.ORDER_PAY_NOT_FOUND);
        orderPay.setStatus(PayStatusEnum.WAIT_PAY.getCode());
        orderPay.setUpdateTime(new Date());
        //修改订单
        boolean result = this.updateById(orderPay);
        if(!result){
            return new ResultObject(ResultEnum.FAIL.getCode(),ResultEnum.FAIL.getMsg());
        }

        //第二步 对用户账户金额增减
        ResultObject userResult =  userServiceFeign.updateMoneyById(payDto.getBuyerId(),payDto.getTotalAmount(),payDto.getPaySn());
        BusinessAssert.isFalse(userResult.getCode() == ResultObjectEnum.SYSTEM_HYSTRIX.getCode(),ExceptionUtil.BuyerExceptionEnum.BUYER_AMOUNT_DEDUCT_FAIL);
        logger.info("<<<<<<<<<<<<<<<OrderPayServiceImpl#callBack方法 进入到TCC分布式事务Try方法中 END");
        return new ResultObject();
    }

    /**
     * TCC confirm方法确认防范
     * @param payDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultObject confirmCallBack(OrderPayDto payDto) {

        logger.info("<<<<<<<<<<<<<<<OrderPayServiceImpl#confirmCallBack方法 进入到TCC分布式事务confirm方法中 START");
        /**
         * 第一步 修改支付订单状态
         * TODO 在TCC confirm方法中，直接将支付订单状态修改为【支付完成】
         */
        OrderPay orderPay = this.baseMapper.selectOne(new QueryWrapper<OrderPay>().eq("pay_sn",payDto.getPaySn()));
        BusinessAssert.notNull(orderPay, ExceptionUtil.OrderPayExceptionEnum.ORDER_PAY_NOT_FOUND);
        orderPay.setStatus(PayStatusEnum.ALREADY_PAY.getCode());
        orderPay.setUpdateTime(new Date());
        //修改订单
        boolean result = this.updateById(orderPay);
        if(!result){
            return new ResultObject(ResultEnum.FAIL.getCode(),ResultEnum.FAIL.getMsg());
        }
        logger.info("<<<<<<<<<<<<<<<OrderPayServiceImpl#confirmCallBack方法 进入到TCC分布式事务confirm方法中 END");
        return new ResultObject();
    }

    /**
     * TCC 取消方法
     * @param payDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultObject cancelCallBack(OrderPayDto payDto) {
        logger.info("<<<<<<<<<<<<<<<OrderPayServiceImpl#cancelCallBack方法 进入到TCC分布式事务cancel方法中 START");
        /**
         * 在取消方法中需要实现幂等性，根据支付订单状态,同时如果取消成功则将状态该为【支付中】
         */
        OrderPay orderPay = this.baseMapper.selectOne(new QueryWrapper<OrderPay>().eq("pay_sn",payDto.getPaySn()));
        /**
         * 如果支付状态是【已支付】和【支付中】则直接返回，否则将支付订单状态修改为【支付中】，这里主要还是为了幂等性
         */
        if(orderPay.getStatus().equals(PayStatusEnum.ALREADY_PAY.getCode()) || orderPay.getStatus().equals(PayStatusEnum.WAIT_PAY.getCode())){
            new ResultObject();
        }
        orderPay.setStatus(PayStatusEnum.WAIT_PAY.getCode());
        orderPay.setUpdateTime(new Date());
        //修改订单
        boolean result = this.updateById(orderPay);
        if(!result){
            return new ResultObject(ResultEnum.FAIL.getCode(),ResultEnum.FAIL.getMsg());
        }
        logger.info("<<<<<<<<<<<<<<<OrderPayServiceImpl#cancelCallBack方法 进入到TCC分布式事务cancel方法中 END");
        return new ResultObject();
    }


}
