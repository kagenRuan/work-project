package com.ruan.yuanyuan.suanfa;

import java.util.Arrays;

/**
 * @ClassName XuanZheSort
 * @Author ruanyuanyuan
 * @Date 2020/8/15-21:16
 * @Version 1.0
 * @Description TODO 选择排序
 * 思路：首先循环一次拿到最小的数并记录其下标。然后再次循环拿最小的下标获取值。
 *      然后再拿最小的数和数组中的数进行比较获取最小的数，并交换位置
 * 时间复杂度O(n2) n平方  不稳定
 **/
public class XuanZheSort {

    public static void main(String[] args) {
        int[] arrays = {8,2,5,1,3,7,4,6};
        sort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

    private static void sort(int[] array){
        for (int i = 0; i <array.length ; i++) {
            for (int j = i+1; j <array.length ; j++) {
                if(array[i] < array[j]){
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }
}
