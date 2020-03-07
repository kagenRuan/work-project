package com.ruan.yuanyuan.设计模式.抽象工厂;

/**
 * @ClassName: JavaCourseFactory
 * @author: ruanyuanyuan
 * @date: 2020/1/26 16:08
 * @version: 1.0
 * @description: Java课程工厂类
 **/
public class JavaCourseFactory implements CourseFactory {
    @Override
    public Video getVideo() {
        return new JavaVideo();
    }

    @Override
    public Article getArticle() {
        return new JavaArticle();
    }
}
