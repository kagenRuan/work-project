package com.ruan.yuanyuan.spring.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Author ruanyuanyuan
 * @Date 2021/1/11-15:44
 * @Version 1.0
 * @Description TODO
 **/
@RestController
public class TestController {

    @RequestMapping("/test")
    public void test(){
        System.out.println("test");
    }
}
