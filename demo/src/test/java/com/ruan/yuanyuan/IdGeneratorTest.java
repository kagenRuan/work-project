package com.ruan.yuanyuan;

import com.baidu.fsg.uid.UidGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName IdGeneratorTest
 * @Author ruanyuanyuan
 * @Date 2020/9/23-14:27
 * @Version 1.0
 * @Description TODO ID生成测试类
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdGeneratorTest {

    @Resource
    private UidGenerator uidGenerator;

    @Test
    public void test(){
       long id = uidGenerator.getUID();
        System.out.println(id);
    }
}
