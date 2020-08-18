package com.ruan.yuanyuan.demo.suanfa;

import java.util.Arrays;

/**
 * @ClassName XuanZheSort
 * @Author ruanyuanyuan
 * @Date 2020/8/15-21:16
 * @Version 1.0
 * @Description TODO 选择排序
 * 思路：首先循环一次拿到最小的数并记录其下标。然后再次循环拿最小的下标获取值。
 *      然后再拿最小的数和数组中的数进行比较获取最小的数，并交换位置
 **/
public class XuanZheSort {

    public static void main(String[] args) {
        int[] arrays = {8,2,5,1,3,7,4,6};
        for(int i=0;i<arrays.length-1;i++){
            int k =i;
            //每循环一次就找到最小记录其下标
            for(int j=i+1;j<arrays.length;j++){
                if(arrays[j] < arrays[k]){
                    k = j;
                }
            }
            //内循环循环完一次就和外循环进行位置交换
            int temp = arrays[i];
            arrays[i] = arrays[k];
            arrays[k] = temp;
        }
        System.out.println(Arrays.toString(arrays));
    }
}
