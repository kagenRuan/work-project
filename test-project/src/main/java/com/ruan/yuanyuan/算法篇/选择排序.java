package com.ruan.yuanyuan.算法篇;

/**
 * @ClassName: 选择排序
 * @author: ruanyuanyuan
 * @date: 2020/5/22 13:20
 * @version: 1.0
 * @description: 选择排序
 * 排序思想：用第一个数与其他数开始比较，如果第一个数比下一个数大，则将大的数放到数组第一个位置，然后从第二个位置开始又跟其他数开始比较，
 * 把小的数放在前面，一次循环吧
 * 时间复杂度：O(n2)
 * 稳定性：不稳定，如果有两个相等的数，在排序后相等数的位置可能发生变化
 **/
public class 选择排序 {

    public static void main(String[] args) {
        int array[] ={3,2,4,1,5,9,7,6,8};
//        int array[] ={3,2,4,1,5,3,7,6,3};这个数组中的最后一个3就会被丢失

        int length = array.length-1;
        for(int i=0;i<length;i++){
            int min = i;
            for(int j=i+1;j<length;j++){
                min = array[j] < array[min] ? j:min;
                System.out.println(min);
            }
            System.out.println("===========");

            int a = array[i];
            array[i] = array[min];
            array[min] = a;
        }
        System.out.println("<<<<<<<>>>>>>>>>>");
        for (int i =0;i<length;i++){
            System.out.println(array[i]);
        }
    }
}
