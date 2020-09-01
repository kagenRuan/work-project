package com.ruan.yuanyuan.suanfa;

import java.util.Arrays;

/**
 * @ClassName MaoPaoSort
 * @Author ruanyuanyuan
 * @Date 2020/8/15-15:26
 * @Version 1.0
 * @Description TODO 冒泡算法 思路：数字两两比较换位置，每比较一次则内循环就少循环一次
 * 时间复杂度O(n)
 **/
public class MaoPaoSort {


    public static void main(String[] args) {
        int[] array = {8,5,9,3,2,5,2,4,7,6,1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int array[]){
        for (int i = 0; i < array.length-1; i++) {
            for (int j = 0; j < array.length-1-i; j++) {
                if(array[j] < array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
    }
}
