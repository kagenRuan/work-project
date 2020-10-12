package com.ruan.yuanyuan.collection.list;

import org.apache.commons.collections.list.SynchronizedList;

import java.util.*;

/**
 * @ClassName TestList
 * @Author ruanyuanyuan
 * @Date 2020/10/12-09:04
 * @Version 1.0
 * @Description TODO 测试List
 **/
public class TestList {

    public static void main(String[] args) {
        List list = new ArrayList();
        //线程安全,底层操作是使用Synchronized
        Vector<Object> vector = new Vector<>();
        //线程安全，传入List,底层操作是使用Synchronized
        List decorate = SynchronizedList.decorate(new LinkedList());
        //线程安全，传入List,底层操作是使用Synchronized
        Collections.synchronizedList(new LinkedList<>());
    }
}
