package com.ruan.yuanyuan.suanfa;

import java.util.Arrays;

/**
 * @ClassName ChaRuSort
 * @Author ruanyuanyuan
 * @Date 2020/8/15-22:29
 * @Version 1.0
 * @Description TODO 插入排序  就是从数组中的第一个元素开始循环，如果数组中的元素第0个元素大于、
 *                            或者小于数组中的第一个元素则进行交换位置，以此进行循环
 **/
public class ChaRuSort {

    public static void main(String[] args) {
        int[] arrays ={7,3,1,5,9,4};
        sort(arrays);
        System.out.println(Arrays.toString(arrays));
    }


    private static void sort(int[] array){
        for (int i = 1; i <array.length ; i++) {
            for (int j = i; j >0 ; j--) {
                if(array[j] < array[j-1]){
                    int tamp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tamp;
                }
            }
        }
    }


















//    public static void sort(int[] array){
//        for (int i = 1; i < array.length; i++) {
//            for (int j = i; j>0; j--) {
//                if(array[j] > array[j-1]){
//                    int temp = array[j];
//                    array[j] = array[j-1];
//                    array[j-1] = temp;
//                }
//            }
//        }
//    }
}
