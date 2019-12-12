package com.ruan.yuanyuan.util;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 22:34
 * version:
 * Description:
 */
public class StringUtils {

    /**
     * 首字母小写
     *
     * @param beanName
     * @return
     */
    public static String loverFirstChar(String beanName) {
        char[] chars = beanName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);

    }

}
