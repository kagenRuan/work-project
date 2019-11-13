package com.ruan.yuanyuan.framework.annoation;

import java.lang.annotation.*;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 15:45
 * version:
 * Description: 用于依赖助于与spring中的@AutoWried注解功能相同
 */
@Target(ElementType.FIELD) //注解用于类上面
@Retention(RetentionPolicy.RUNTIME) //运行时使用
@Documented
public @interface RAutoWried {

    String value() default "";
}
