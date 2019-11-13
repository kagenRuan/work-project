package com.ruan.yuanyuan;

import com.ruan.yuanyuan.spring.RApplicationContext;
import com.ruan.yuanyuan.spring.config.RConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringMvcApplicationTests {

    @Test
    public void contextLoads() {

        RApplicationContext rApplicationContext = new RApplicationContext(RConfig.class);
        RConfig rConfig = (RConfig) rApplicationContext.getBean("rConfig");
        rConfig.test();
    }


}
