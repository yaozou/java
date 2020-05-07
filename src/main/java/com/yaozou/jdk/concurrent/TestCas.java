package com.yaozou.jdk.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: CAS
 * @Author yao.zou
 * @Date 2019/9/16 0016
 * @Version V1.0
 **/
public class TestCas {
    private static int synCount = 0;
    private static AtomicInteger atomicCount = new AtomicInteger();
    public static void main(String[] args){
        // synchronized 悲观锁
        testSynchronized();
        // atomic 乐观锁
        testAtomic();
        testCas();
    }

    private static void testSynchronized(){
        for (int i = 0 ;i < 100;i++){
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (TestCas.class){
                    synCount++;
                }
            }).start();
        }
        try {
            Thread.sleep(3000);
            System.out.println("synchronized count :"+synCount);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testAtomic(){
        for (int i = 0 ;i < 100;i++){
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                    atomicCount.incrementAndGet();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            Thread.sleep(3000);
            System.out.println("atomic count :"+atomicCount.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static MyAtomicInteger myAtomicCount = new MyAtomicInteger();
    private static void testCas(){
        for (int i = 0 ;i < 100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                        myAtomicCount.incrementAndGet();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(3000);
            System.out.println("atomic count :"+myAtomicCount.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

