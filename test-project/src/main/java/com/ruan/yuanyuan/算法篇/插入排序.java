package com.ruan.yuanyuan.算法篇;

/**
 * @ClassName: 插入排序
 * @author: ruanyuanyuan
 * @date: 2020/5/22 14:39
 * @version: 1.0
 * @description: 插入排序
 **/
public class 插入排序 {

    public static void main(String[] args) {
       int array[] = Base.randInt();//{1,3,4,2,5}
       for(int i=1;i<array.length;i++){
           for(int j = i;j>0;j--){
                if(array[j] < array[j-1]){
                    int temp = array[j];
                    array[j]=array[j-1];
                    array[j-1]=temp;
                }
           }
       }
       Base.resultJoinStr(array);
    }
}
