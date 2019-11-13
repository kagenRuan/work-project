package com.ruan.yuanyuan.util;

import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-09-04
 * Time: 08:30
 * version:
 * Description:
 */
@FunctionalInterface
public interface ToBigDecimalFunction<T> {

    BigDecimal applyAsBigDecimal(T value);

}
