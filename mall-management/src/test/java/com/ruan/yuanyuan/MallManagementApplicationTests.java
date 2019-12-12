package com.ruan.yuanyuan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallManagementApplicationTests {


    @Autowired
    private HashOperations hashOperations;

    @Test
    public void testRedis() {
        hashOperations.put("shiro:cache:authorizationCache:", "adad", "sss");

    }


}
