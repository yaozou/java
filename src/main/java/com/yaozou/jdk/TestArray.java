package com.yaozou.jdk;

public class TestArray {
    public static void main(String[] args){
        int[] arr=new int[3];
        arr[0]=10;
        arr[1]=20;
        arr[2]=70;
        System.out.println(arr);

        short[] arr2=new short[2];
        arr2[0]=30;
        arr2[1]=40;
        System.out.println(arr2);

        int[] arr3=arr;
        arr3[0]=100;
        arr3[1]=200;
        arr3[2]=300;
        System.out.println(arr);
    }
}
