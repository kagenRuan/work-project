package com.ruan.yuanyuan.suanfa;

/**
 * @ClassName ErFenChaZhao
 * @Author ruanyuanyuan
 * @Date 2020/9/8-14:32
 * @Version 1.0
 * @Description TODO 二分查找算法
 *                   特点：数组必须有序
 **/
public class ErFenChaZhao {


    public static void main(String[] args) {
//        int[] array = new int[100];
//        for (int i = 0; i < 100; i++) {
//            array[i] = i;
//        }
        int[] array = {1,3,5,7,9};
        //二分查找未优化
        int index = getIndex(array,0,array.length-1,1);
        System.out.println(index);
        //二分查找优化之后
        int index1 = getIndex1(array,0,array.length-1,1);
        System.out.println(index1);
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/9/11 13:36
     * @Description:
     * @param array:  原数组
     * @param left:   左边索引
     * @param right:  右边索引
     * @param findVal: 要查找的数
     * @return: int 返回查找数的下标
     **/
    public static int getIndex(int[] array, int left, int right, int findVal){
        if(left > right){
            return -1;
        }

        int mid = (left + right) >> 1;

        if(findVal < array[mid]){
           return getIndex(array,left,mid-1,findVal);
        }else if(findVal > array[mid]){
            return getIndex(array,mid+1,right,findVal);
        }else {
            return mid;
        }

    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/9/8 15:13
     * @Description: 使用插值算法对二分查找进行优化
     **/
    public static int getIndex1(int[] array,int left,int right,int findValue){
        System.out.println("二分查找优化之后一共循环的了几次");
        /**
         * @Description: 如果left大于right则说明没有在数组中找到该数字
         **/
        if(left > right || array[0] > findValue || array[array.length-1] < findValue){
            return -1;
        }
        //找到中间数下标
        int mid = left + (right - left) * (findValue - array[left]) / (array[right] - array[left]);
        //找到中间数值
        int midValue = array[mid];
        //判断并递归查询
        if(findValue > midValue){
            return getIndex(array,mid+1,right,findValue);
        }else if(findValue < midValue) {
            return getIndex(array,left,mid-1,findValue);
        }else {
            return mid; //返回下标
        }
    }
}
