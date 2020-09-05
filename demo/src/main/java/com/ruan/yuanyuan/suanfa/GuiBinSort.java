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
//        int[] array = {1,3,5,6,2,4,8,9,10,11,12};
        int[] array = {1,4,7,8,3,6,9};
        sort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));
    }

    private static void sort(int[] array,int left,int right) {
        if(left == right) return;
        //将数组分为两半
        int mid = left + ((right-left) >> 1);
        //左边排序
        sort(array,left,mid);
        //右边排序
        sort(array,mid+1,right);
        merge(array,left,mid+1,right);
    }

    private static void merge(int[] array,int left,int right,int rightBound) {
        int mid = right-1;
        int[] temp = new int[rightBound-left+1];

        int i = left;
        int j = right;
        int k = 0;

        while (i <= mid && j<= rightBound){
            if(array[i] <= array[j]){
                temp[k] = array[i];
                i++;
            }else {
                temp[k] = array[j];
                j++;
            }
            k++;
        }


        while (i<=mid){
            temp[k++] = array[i++];
        }

        while (j<=rightBound){
            temp[k++] = array[j++];
        }

        for (int l = 0; l <temp.length-1 ; l++) {
            array[left+l] = temp[l];
        }

    }

    private static void swap(int[] array,int left,int right){
        int temp  =array[left];
        array[left] = array[right];
        array[right] = temp;
    }
}
