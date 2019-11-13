package com.ruan.yuanyuan.集合框架;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {

    public static void main(String[] args) {
        List lit = new Vector();
        List lit1 = Collections.synchronizedList(new ArrayList<>());
        List list = new CopyOnWriteArrayList();
        list.add(1);
        list.add(2);


    }
}
