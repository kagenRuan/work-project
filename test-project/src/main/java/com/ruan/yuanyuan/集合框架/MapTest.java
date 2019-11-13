package com.ruan.yuanyuan.集合框架;


import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {


    public static void main(String[] args) {
        Map map = new HashMap<>();
        Map map1 = new Hashtable();
        Map map2 = new ConcurrentHashMap();
        Map map4 = Collections.synchronizedMap(new HashMap<>());
    }
}
