package com.ruan.yuanyuan.util;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 10:39
 * version:1.0
 * Description:支付单号工具类
 */
public class PaySnUtile {

    private static final String PAY_SN ="paysn";

    public static String getPaySn(){
        StringBuilder sb = new StringBuilder(PAY_SN);
        Long result = System.currentTimeMillis();
        sb.append(result);
        return sb.toString();
    }
}
