package com.ruan.yuanyuan.demo.suanfa;

import java.util.Arrays;

/**
 * @ClassName MaoPaoSort
 * @Author ruanyuanyuan
 * @Date 2020/8/15-15:26
 * @Version 1.0
 * @Description TODO 冒泡算法 思路：数字两两比较换位置，每比较一次则内循环就少循环一次
 **/
public class MaoPaoSort {

    public static void main(String[] args) {
        int[] array = {6,3,9,5,7,2,4,1,6};
        int temp =0;
        for(int i=0;i<array.length -1;i++){
            for(int j=0;j<array.length-1-i;j++){
                if(array[j] > array[j+1]){
                    temp = array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }
}
