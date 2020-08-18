package com.ruan.yuanyuan.demo.suanfa;

import java.util.Arrays;

/**
 * @ClassName ChaRuSort
 * @Author ruanyuanyuan
 * @Date 2020/8/15-22:29
 * @Version 1.0
 * @Description TODO 插入排序
 **/
public class ChaRuSort {

    public static void main(String[] args) {
        int[] arrays ={7,3,1,5,9,4};
        for (int i = 1; i < arrays.length; i++) {
            for (int j = i; j >0; j--) {
                if(arrays[j] < arrays[j - 1]){
                    int temp = arrays[j];
                    arrays[j] =  arrays[j-1];
                    arrays[j-1]=temp;
                }

            }
        }
        System.out.println(Arrays.toString(arrays));
    }
}
