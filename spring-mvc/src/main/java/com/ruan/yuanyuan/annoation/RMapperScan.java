package com.ruan.yuanyuan.annoation;

import java.lang.annotation.*;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-10-02
 * @Time: 22:58
 * @version:1.0
 * @Description: 主要用于扫描mapper
 */
@Target({ElementType.TYPE,ElementType.METHOD}) //注解用于类和方法上面
@Retention(RetentionPolicy.RUNTIME) //运行时使用
@Documented
public @interface RMapperScan {

    String value() default "";
}
