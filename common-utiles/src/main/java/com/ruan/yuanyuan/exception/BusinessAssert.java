package com.ruan.yuanyuan.exception;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-24
 * Time: 13:46
 * version:
 * Description:
 */
public abstract class BusinessAssert{


    public BusinessAssert() {
    }

    public static void isTrue(@Nullable boolean expression,ExceptionInterface exception) {
        if (!expression) {
            throw new BusinessException(exception.getCode(),exception.getMessage());
        }
    }

    public static void isFalse(@Nullable boolean expression,ExceptionInterface exception) {
        if(expression){
            throw new BusinessException(exception.getCode(),exception.getMessage());
        }
    }

    public static void isNull(@Nullable Object object,ExceptionInterface exception) {
        if (object != null) {
            throw new BusinessException(exception.getCode(),exception.getMessage());
        }
    }

    public static void notNull(@Nullable Object object,ExceptionInterface exception) {
        if (object == null) {
            throw new BusinessException(exception.getCode(),exception.getMessage());
        }
    }


    public static void isBlank(@Nullable String str,ExceptionInterface exception) {
        if (StringUtils.hasLength(str)) {
            throw new BusinessException(exception.getCode(),exception.getMessage());
        }
    }

    public static void notBlank(@Nullable String str,ExceptionInterface exception) {
        if (!StringUtils.hasLength(str)) {
            throw new BusinessException(exception.getCode(),exception.getMessage());
        }
    }


    public static void notBlank(@Nullable String str,ExceptionInterface exception,String... args) {
        if (!StringUtils.hasLength(str)) {
            throw new BusinessException(exception.getCode(),exception.getMessage(),args);
        }
    }

    public static void notEmpty(@Nullable Object[] array,ExceptionInterface exception) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BusinessException(exception.getCode(),exception.getMessage());
        }
    }

    public static void isEmpty(@Nullable Object[] array,ExceptionInterface exception) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new BusinessException(exception.getCode(),exception.getMessage());
        }
    }


}
