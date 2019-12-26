package com.ruan.yuanyuan.service;

import com.ruan.yuanyuan.dto.OrderPayDto;
import com.ruan.yuanyuan.entity.ResultObject;

/**
 * @ClassName: IOrderCallBackService
 * @author: ruanyuanyuan
 * @date: 2019/12/25 18:23
 * @version: 1.0
 * @description: 所有订单支付的回调接口
 **/
@FunctionalInterface
public interface IPayCallBackService {

    ResultObject callBack(OrderPayDto orderPayDto);

}
