package com.ruan.yuanyuan.设计模式.抽象工厂;

/**
 * @ClassName: Test
 * @author: ruanyuanyuan
 * @date: 2020/1/26 16:27
 * @version: 1.0
 * @description:
 **/
public class Test {

    public static void main(String[] args) {
        CourseFactory courseFactory = new JavaCourseFactory();
        Video video = courseFactory.getVideo();
        Article article = courseFactory.getArticle();
        video.produce();
        article.produce();
    }
}
