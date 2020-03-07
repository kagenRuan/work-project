package com.ruan.yuanyuan.设计模式.抽象工厂;

/**
 * @ClassName: JavaArticle
 * @author: ruanyuanyuan
 * @date: 2020/1/26 16:12
 * @version: 1.0
 * @description: java笔记
 **/
public class JavaArticle extends Article {
    @Override
    public void produce() {
        System.out.println("编写Java笔记");
    }
}
