package com.ruan.yuanyuan.算法篇;

/**
 * @ClassName: 冒泡排序
 * @author: ruanyuanyuan
 * @date: 2020/5/22 13:51
 * @version: 1.0
 * @description: 冒泡排序
 * 排序思路：从第一个数开始，跟后面每一个数排序，如果比下一位数大，则两两换位，每次比较都是这样
 * 时间复杂度：On2
 **/
public class 冒泡排序 {

    public static void main(String[] args) {
        int array[] =  Base.randInt();
        Base.resultJoinStr(array);
        System.out.print("======");

        int length = array.length;
        for(int i=0;i<length-1;i++){
            for(int j = i+1;j<length;j++){
               if(array[i] > array[j]){
                   int a = array[i];
                   array[i] = array[j];
                   array[j] = a;
               }
            }
        }

        Base.resultJoinStr(array);
    }


}
