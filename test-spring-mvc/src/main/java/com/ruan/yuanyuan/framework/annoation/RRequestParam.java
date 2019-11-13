package com.ruan.yuanyuan.framework.annoation;

import java.lang.annotation.*;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 22:25
 * version:
 * Description:
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RRequestParam {

    String value() default "";

}
