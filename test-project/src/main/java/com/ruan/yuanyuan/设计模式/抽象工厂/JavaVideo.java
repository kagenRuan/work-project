package com.ruan.yuanyuan.设计模式.抽象工厂;

/**
 * @ClassName: JavaVideo
 * @author: ruanyuanyuan
 * @date: 2020/1/26 16:09
 * @version: 1.0
 * @description:
 **/
public class JavaVideo extends Video {

    @Override
    public void produce() {
        System.out.println("录制Java视频");
    }
}
