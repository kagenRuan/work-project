package com.ruan.yuanyuan.suanfa;

import java.util.Arrays;

/**
 * @ClassName KuaiSuSort
 * @Author ruanyuanyuan
 * @Date 2020/8/15-15:32
 * @Version 1.0
 * @Description TODO 快速排序
 * 思路：首先在数组中找到一个中间数，
 * 第一步从左边第一个开始循环找到大于等于中间数，第二步从右边开始循环找到小于等于中间数，然后将这两个数进行换位
 **/
public class KuaiSuSort {

    public static void main(String[] args) {
        int[] array = {1,5,1,3,3,2,7,8,0};
        sort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));

    }


    private static void sort(int[] array,int left,int right){
            if(left >= right) return;
            int mid = merge(array,left,right);
            sort(array,left,mid-1);
            sort(array,mid+1,right);

    }

    private static int merge(int[] array,int left,int right){

        int mid = array[right];
        int i = left;
        int j = right-1;

        while (i <= j){

            while (i <= j && array[i] <= mid){
                i++;
            }

            while (i <= j && array[j] >= mid){
                j--;
            }

            if(i < j){
                swap(array,i,j);
            }
        }
        swap(array,i,right);
//        System.out.println(Arrays.toString(array));
        return i;
    }









//    private static void sort(int[] array,int left,int right){
//        if(left >= right)return;
//        int mind = protiton(array,left,right);
//        sort(array,left,mind-1);
//        sort(array,mind+1,right);
//    }
//
//    private static int protiton(int[] array,int left,int right){
//
//        int i = left;
//        int j = right-1;
//        int provit = array[right];//中间值
//
//        while (i <= j){
//
//            //首先循环判断左边的数字小于中间值
//            while (i <= j && array[i] <= provit){
//                i++;
//            }
//            //再循环判断右边的数字大于中间值
//            while (i <= j && array[j] > provit){
//                j--;
//            }
//
//            if(i < j){
//                swap(array,i,j);
//            }
//
//        }
//        swap(array,i,right);
//        return i;
//    }

    private static void swap(int[] array,int leftBound,int rightBound){
            int temp = array[leftBound];
            array[leftBound] = array[rightBound];
            array[rightBound] = temp;
    }








    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/8/15 16:33
     * @Description: 排序调用方法
     * @param array:
     * @return: void
     **/
//    public static void sort(int[] array,int left,int right){
//        if(left >= right)return;
//        int mid = potiton(array,left,right);
//        sort(array,left,mid-1);
//        sort(array,mid+1,right);
//    }
//
//    /**
//     * @Author: ruanyuanyuan
//     * @Date: 2020/8/15 16:33
//     * @Description: 找中间轴数据
//     * @param array: 数组
//     * @param left:  左边数据下标
//     * @param right: 右边数据下标
//     * @return: void
//     **/
//    public static int potiton(int[] array,int left,int right){
//
//        int i = left;
//        int j = right-1;
//        int prvoit = array[right];
//
//        while (i < j){
//
//            //先循环左边,判断左边的数据是否小于中间数,如果小于等于则继续循环
//            while (i <= j && array[i] <= prvoit){
//                i++;
//            }
//
//            //循环右边,判断是否大于中轴数，如果大于则继续循环
//            while (i <= j && array[j] > prvoit){
//                j--;
//            }
//
//            if(i < j){
//                swap(array,i,j);
//            }
//        }
//        swap(array,i,right);
//        return i;
//    }
//
//    public static void swap(int[] array,int left,int right){
//        int temp = array[left];
//        array[left] = array[right];
//        array[right] = temp;
//    }
}
