package com.ruan.yuanyuan.framework.annoation;

import java.lang.annotation.*;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 15:44
 * version:
 * Description: 用于service层与spring中的@Service注解功能类似
 */
@Target(ElementType.TYPE) //注解用于类上面
@Retention(RetentionPolicy.RUNTIME) //运行时使用
@Documented
public @interface RService {

    String value() default "";
}
