package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.dto.ProductDto;
import com.ruan.yuanyuan.entity.Order;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-22
 * Time: 09:10
 * version:
 * Description: 订单服务接口
 */
public interface IOrderService extends IService<Order>{

    /**
     * 创建订单
     * @param productDto
     * @return boolean
     */
    List<Order> createOrder(List<ProductDto> productDto);


}
