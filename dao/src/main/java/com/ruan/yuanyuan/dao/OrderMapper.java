package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.entity.Order;
import com.ruan.yuanyuan.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-22
 * Time: 14:42
 * version:1.0
 * Description:订单DAO
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询所有订单
     *
     * @return
     */
    List<OrderVo> findAll();

    /**
     * 添加订单
     *
     * @param order
     * @return
     */
    Integer add(@Param("order") Order order);
}
