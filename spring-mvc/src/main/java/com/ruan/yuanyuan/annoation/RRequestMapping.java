package com.ruan.yuanyuan.annoation;

import java.lang.annotation.*;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 17:34
 * version:
 * Description: 用于url映射与spring的@RequestMapping用法相同
 */
@Target({ElementType.TYPE,ElementType.METHOD}) //注解用于类和方法上面
@Retention(RetentionPolicy.RUNTIME) //运行时使用
@Documented
public @interface RRequestMapping {

    String value() default "";
}
