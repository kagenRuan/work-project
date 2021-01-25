package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.app.dao.OrderDao;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.vo.order.OrderVO;
import io.renren.modules.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName OrderServiceImpl
 * @Author ruanyuanyuan
 * @Date 2021/1/24-22:22
 * @Version 1.0
 * @Description TODO 订单接口实现类
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<OrderVO> list(Integer userId) {
        List<OrderVO> list = orderDao.list(userId);
        return list;
    }
}
