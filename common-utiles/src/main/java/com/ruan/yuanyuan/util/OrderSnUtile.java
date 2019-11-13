package com.ruan.yuanyuan.util;

import java.io.Serializable;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 10:42
 * version:1.0
 * Description:订单编号工具
 */
public class OrderSnUtile implements Serializable {

    private static final long serialVersionUID = -4521961069551950396L;

    //自定义的
    private static final String ORDER_SN="order";

    /**
     * 获取订单编号
     * @return
     */
    public static String getOrderSn(){
        StringBuilder sb = new StringBuilder(ORDER_SN);
        Long result = System.currentTimeMillis();
        sb.append(result);
        return sb.toString();
    }
}
