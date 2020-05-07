package com.yaozou.jdk.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: TODO
 * @Author yao.zou
 * @Date 2019/9/16 0016
 * @Version V1.0
 **/
public class TestVolatileAndAtomic {
    // 实际运算每次结果都不一样
    //public static int count = 0;
    public static volatile int count = 0;
    public static AtomicInteger count2 = new AtomicInteger();
    public static void inc(){
        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        count ++;
        count2.incrementAndGet();
    }

    public static void main(String[] args) {
        int n = 0;
        while (n < 10){
            n++;
            //同时启动1000个线程，去进行i++运算，看看实际结果
            for (int i = 0; i < 1000; i++) {
                new Thread(() -> TestVolatileAndAtomic.inc()).start();
            }

            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("第"+n+"次运行结果:"+count+" and "+count2.get());
        }
    }

}
