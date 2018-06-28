package com.yaozou.jdk;

import java.util.ArrayList;
import java.util.List;

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

        List<Long> orderIdsList = new ArrayList<Long>();
        orderIdsList.add(1L);
        orderIdsList.add(2L);
        Long[] orderIds = new Long[orderIdsList.size()];

        for (Long or:orderIdsList.toArray(orderIds)) {
            System.out.println(or);
        }
    }
}
