package com.yaozou.jdk.base;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception{
        /*Map<String,Integer> map = new HashMap<>(2);

        for (int i = 1;i <= 10;i++){
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0;j < 100;j++){
                        map.put(j+""+num,j);
                    }
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(map.size());*/

        int position = 3;
        /*System.out.println(position<<1);*/
        System.out.println((position<<1)+1);
    }
}
