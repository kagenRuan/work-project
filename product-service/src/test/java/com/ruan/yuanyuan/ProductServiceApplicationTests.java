package com.ruan.yuanyuan;

import com.ruan.yuanyuan.cache.RedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceApplicationTests {

    @Autowired
    private RedisDao redisDao;

    @Test
    public void contextLoads() {

        String getvalue = redisDao.getInstance().get("yyyyyyy");
        System.out.println(getvalue);
    }

}
