package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.vo.order.OrderVO;

import java.util.List;

/**
 * @ClassName OrderService
 * @Author ruanyuanyuan
 * @Date 2021/1/24-22:21
 * @Version 1.0
 * @Description TODO 订单接口
 **/
public interface OrderService extends IService<OrderEntity> {

    List<OrderVO> list(Integer userId);
}
