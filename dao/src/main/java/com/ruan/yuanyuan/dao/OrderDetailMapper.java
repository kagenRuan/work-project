package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.entity.OrderDetail;
import com.ruan.yuanyuan.vo.OrderDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-22
 * Time: 14:43
 * version:1.0
 * Description:订单详情
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    /**
     * 根据订单ID查询订单详情
     *
     * @param orderId 订单ID
     * @return OrderDetailVo
     */
    List<OrderDetailVo> findByOrderId(@Param("orderId") String orderId);

    /**
     * 添加订单详情
     *
     * @param orderDetails 订单详情集合
     * @return Integer
     */
    Integer add(@Param("orderDetails") List<OrderDetail> orderDetails);
}
