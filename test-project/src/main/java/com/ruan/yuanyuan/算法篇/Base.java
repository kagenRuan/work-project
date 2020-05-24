package com.ruan.yuanyuan.算法篇;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @ClassName: Base
 * @author: ruanyuanyuan
 * @date: 2020/5/22 14:31
 * @version: 1.0
 * @description:
 **/
public class Base {

    //查收0-10之间的随机数
    static int[] randInt(){
        int array[]= new int[10];
        for (int i=0;i<10;i++){
            Random random = new Random();
            array[i] = random.nextInt(10);
        }
        return array;
    }

    //打印数组
    static void resultJoinStr(int[] array){
        if(!ObjectUtils.isEmpty(array)){
            String result = Arrays.stream(array).mapToObj(obj -> obj+"").collect(Collectors.joining(","));
            System.out.println(result);
        }
    }
}
