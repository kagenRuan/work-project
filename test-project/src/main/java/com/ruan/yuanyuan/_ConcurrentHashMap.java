package com.ruan.yuanyuan;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: _ConcurrentHashMap
 * @author: ruanyuanyuan
 * @date: 2020/5/30 14:03
 * @version: 1.0
 * @description:分析 ConcurrentHashMap
 **/
public class _ConcurrentHashMap {

    public static void main(String[] args) {
        Map<String,Object> map = new ConcurrentHashMap<>();
        for(int i=0;i<10;i++){
            map.put("a"+i,i);
        }
        System.out.println(map.size());

    }
}
