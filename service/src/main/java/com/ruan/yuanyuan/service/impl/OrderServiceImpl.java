package com.ruan.yuanyuan.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.OrderMapper;
import com.ruan.yuanyuan.dto.ProductDto;
import com.ruan.yuanyuan.entity.Order;
import com.ruan.yuanyuan.entity.OrderDetail;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.enums.OrderStatusEnum;
import com.ruan.yuanyuan.enums.ResultEnum;
import com.ruan.yuanyuan.enums.Yum;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IOrderDetailService;
import com.ruan.yuanyuan.service.IOrderService;
import com.ruan.yuanyuan.util.OrderSnUtile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-22
 * Time: 09:11
 * version:1.0
 * Description:订单服务实现
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 创建订单
     *
     * @param productDtoList
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Order> createOrder(List<ProductDto> productDtoList) {
        logger.info("<<<<<<OrderServiceImpl#createOrder --创建订单 参数:productDtoList:{}", JSON.toJSONString(productDtoList));

        BusinessAssert.isFalse(ObjectUtils.isEmpty(productDtoList), ExceptionUtil.OrderExceptionEnum.ORDER_PRODUCT_NOT_NULL);
        Map<String, List<ProductDto>> param = productDtoList.stream().collect(Collectors.groupingBy(ProductDto::getShopId));

        List<Order> orderList = new ArrayList<>();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (Map.Entry<String, List<ProductDto>> entry : param.entrySet()) {
            //订单编号
            String orderSn = OrderSnUtile.getOrderSn();


            List<ProductDto> productList = entry.getValue();
            //订单信息
            Order order = new Order();
            order.setOrderSn(orderSn);
            order.setStatus(OrderStatusEnum.STAY_PAY.getCode());
            order.setCreateTime(new Date());
            //验证商品参数以及计算订单总额
            BigDecimal amount = BigDecimal.ZERO;
            for (ProductDto product : productList) {
                BusinessAssert.notNull(product.getId(), ExceptionUtil.ProductExceptionEnum.PRODUCT_ID_NOT_NULL);
                BusinessAssert.isFalse(product.getNum() <= 0, ExceptionUtil.ProductExceptionEnum.PRODUCT_NUM_NOT_NULL);
                /**
                 * TODO 这里省略校验查询商品，后期加上
                 */
                BigDecimal productPrice = new BigDecimal(product.getNum()).multiply(product.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                amount = amount.add(productPrice);
                order.setAmount(amount);
                order.setShopId(product.getShopId());
                //订单详情
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderSn(orderSn);
                orderDetail.setCreateTime(new Date());
                orderDetail.setIdProduct(product.getId());
                orderDetail.setNum(product.getNum());
                orderDetail.setPrice(product.getPrice());
                orderDetail.setTotalPrice(productPrice);
                orderDetail.setName("sss");
                orderDetailList.add(orderDetail);
            }
            orderList.add(order);
        }
        //保存订单
        boolean saveOrder = super.saveBatch(orderList);
        if (!saveOrder) {
            logger.error("<<<<<<OrderServiceImpl#addOrder --添加订单失败 参数:order:{}", JSON.toJSONString(orderList));
            BusinessAssert.isTrue(saveOrder, ExceptionUtil.OrderExceptionEnum.ORDER_CREATE_FAIL);
        }
        //保存订单详情
        boolean saveOrderDetail = orderDetailService.saveBatch(orderDetailList);
        if (!saveOrderDetail) {
            logger.error("<<<<<<<OrderServiceImpl#addOrder --添加订单详情失败 参数:orderDetail:{}", JSON.toJSONString(orderDetailList));
            BusinessAssert.isTrue(saveOrderDetail, ExceptionUtil.OrderExceptionEnum.ORDER_DETAIL_FAIL);
        }
        return orderList;
    }

    @Override
    public void getOrder() {
        System.out.println("sss");
    }


    /**
     * 修改订单状态
     * @param orderId 订单ID
     * @return ResultObject
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultObject updateOrder(String orderId,String status) {
        ResultObject resultObject = new ResultObject();
        //查询订单
        Order order = this.baseMapper.selectById(orderId);
        BusinessAssert.notNull(order,ExceptionUtil.OrderExceptionEnum.ORDER_NOT_EXITS);
        order.setStatus(status);
        order.setPayStatus(Yum.YES.getCode());
        order.setUpdateTime(new Date());
        //修改订单
        boolean result = this.updateById(order);
        resultObject.setCode(ResultEnum.getResultEnum(result).getCode());
        resultObject.setMsg(ResultEnum.getResultEnum(result).getMsg());
        return resultObject;
    }

}
