package com.ruan.yuanyuan.设计模式.抽象工厂;

/**
 * @ClassName: CourseFactory
 * @author: ruanyuanyuan
 * @date: 2020/1/26 16:03
 * @version: 1.0
 * @description: 课程工厂
 **/
public interface CourseFactory {

    Video getVideo();//获取视频

    Article getArticle();//获取笔记
}
