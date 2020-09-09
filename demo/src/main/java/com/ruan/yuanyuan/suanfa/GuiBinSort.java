package com.ruan.yuanyuan.suanfa;

import java.util.Arrays;

/**
 * @ClassName GuiBinSort
 * @Author ruanyuanyuan
 * @Date 2020/8/26-17:21
 * @Version 1.0
 * @Description TODO 归并排序
 **/
public class GuiBinSort {

    public static void main(String[] args) {
        int[] array = {1,4,7,8,3,6,9,2,5};
        int[] temp = new int[array.length];
        sort(array,0,array.length-1,temp);
        System.out.println(Arrays.toString(array));

    }


    public static void sort(int[] array,int left,int right,int[] temp){
        if(left < right){
            int mid = (left + right) / 2;
            //向左递归
            sort(array,left,mid,temp);
            //向右递归
            sort(array,mid+1,right,temp);
            merge(array,left,mid,right,temp);
        }
    }



    public static void merge(int[] array,int left,int mid,int right,int[] temp){
        int i = left;//确定数组左边初始索引
        int j = mid + 1;//右边数组初始索引
        int t = 0;//临时数组temp的初始索引


        while (i <= mid && j <= right){
            //循环左边的数组中的每一个元素与右边数组中的每一个元素进行比较
            if(array[i] < array[j]){
                temp[t] = array[i];
                i++;
            }else{ //循环右边的数组中的每一个元素与左边数组中的每一个元素进行比较
                temp[t] = array[j];
                j++;
            }
            t++;
        }

        //如果左边数组没有循环比较完，则将剩余复制到数组中
        while (i <= mid){
            temp[t] = array[i];
            i++;
            t++;
        }

        //如果右边边数组没有循环比较完，则将剩余复制到数组中
        while (j <= right){
            temp[t] = array[j];
            j++;
            t++;
        }

        //最后一次合并
        t=0;
        int tempLeft = left;
        while (tempLeft <= right ){
            array[tempLeft] = temp[t];
            t++;
            tempLeft++;
        }
    }
}
