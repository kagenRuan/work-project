package com.ruan.yuanyuan;

import com.ruan.yuanyuan.sharding_jdbc_example.entity.BOrder;
import com.ruan.yuanyuan.sharding_jdbc_example.entity.Position;
import com.ruan.yuanyuan.sharding_jdbc_example.entity.PositionDetail;
import com.ruan.yuanyuan.sharding_jdbc_example.repository.BOrderRepository;
import com.ruan.yuanyuan.sharding_jdbc_example.repository.PositionDetailRepository;
import com.ruan.yuanyuan.sharding_jdbc_example.repository.PositionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

/**
 * @ClassName PositionTest
 * @Author ruanyuanyuan
 * @Date 2020/9/22-23:28
 * @Version 1.0
 * @Description TODO  Position职位分库测试
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes =DemoApplication.class)
public class PositionTest {

    @Autowired
    private PositionRepository repository;
    @Autowired
    private PositionDetailRepository positionDetailRepository;
    @Autowired
    private BOrderRepository bOrderRepository;

    @Test
    public void add(){
        for (int i = 1; i <=20 ; i++) {
            Position position = new Position();
            position.setCity("成都"+i);
            position.setName("成都"+i);
            position.setSalary("成都"+i);
            repository.save(position);
            PositionDetail detail = new PositionDetail();
            detail.setPid(position.getId());
            detail.setDescription("成都"+i);
            positionDetailRepository.save(detail);
        }

    }

    @Test
    @Repeat(100)
    public void addBOrder(){
        BOrder order = new BOrder();
        int companyId =new Random().nextInt(10);
        order.setCompanyId(companyId);
        order.setCreateTime(new Date());
        order.setDel(true);
        order.setName("成都"+companyId);
        order.setOperateTime(new Date());
        bOrderRepository.save(order);
    }

}
