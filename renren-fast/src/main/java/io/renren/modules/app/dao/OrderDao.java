package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.vo.order.OrderVO;
import io.renren.modules.app.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @ClassName OrderDao
 * @Author ruanyuanyuan
 * @Date 2021/1/24-22:19
 * @Version 1.0
 * @Description TODO 订单dao
 **/
@Mapper
public  interface OrderDao extends BaseMapper<OrderEntity> {
    /**
     * 根据用户ID查询订单列表
     **/
    List<OrderVO> list(@Param("userId") Integer userId);

}
