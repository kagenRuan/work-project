package com.ruan.yuanyuan.util;

import java.awt.image.BufferedImage;

/**
 * @ClassName: VerifyCodeUtil
 * @author: ruanyuanyuan
 * @date: 2020/7/5 19:29
 * @version: 1.0
 * @description: 验证码方法
 **/
public class VerifyCodeUtil {

    /**
     * 生成验证码
     * @return
     */
    public static String generateTextCode(){

        return "";
    }

    /**
     * 生成图片验证码
     * @return
     */
    public static BufferedImage generateImageCode(String verifyCode,int width,int height){
        BufferedImage bufferedImage = new BufferedImage(1,1,1);
        return bufferedImage;
    }
}
