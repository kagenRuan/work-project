package com.ruan.yuanyuan.annoation;

import java.lang.annotation.*;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 15:43
 * version:
 * Description: 用于控制器与spring中的@Controller注解功能相同
 */
@Target(ElementType.TYPE) //注解用于类上面
@Retention(RetentionPolicy.RUNTIME) //运行时使用
@Documented
public @interface RController {

    String value() default "";
}
