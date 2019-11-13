package com.ruan.yuanyuan.util;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 11:22
 * version:
 * Description: 消息ID生成工具
 */
public class MessageIdUtile {
    /**
     * TODO 用于生成消息ID,目前只用时间毫秒数，后期会完善
     */
    public static String getMessageId(String arg){
        StringBuilder sb = new StringBuilder();
        sb.append(arg);
        return sb.toString();
    }
}
