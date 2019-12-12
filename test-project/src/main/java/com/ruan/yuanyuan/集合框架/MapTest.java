package com.ruan.yuanyuan.集合框架;


import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {


    public static void main(String[] args) {
        Map map = new HashMap<>();
//        Map map1 = new Hashtable();
//        Map map2 = new ConcurrentHashMap();
//        Map map4 = Collections.synchronizedMap(new HashMap<>());

        //
        int hash = hash("16");
//        System.out.printf(hash+"");
        int a = hash % 18;//取模
        System.out.println(a + "");
        int b = hash & 17;//亦或运算
        System.out.println(b + "");
    }

    //HashMap的计算Index值
    private static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
