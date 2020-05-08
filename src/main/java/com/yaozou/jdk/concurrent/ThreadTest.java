package com.yaozou.jdk.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 线程
 * @Auther: yaozou
 * @Date: 2018/9/17 10:16
 */
public class ThreadTest extends Thread {
    private static final AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        (new ThreadTest()).start();

    }

    @Override
    public void run() {
        Thread.currentThread().interrupt();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("----1");
            Thread.currentThread().interrupt();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("----2");
            Thread.currentThread().interrupt();
            // 中断重置
            /*Thread.interrupted();*/
            // 判断是否在中断中
            /*Thread.currentThread().isInterrupted();*/
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("----3");
        }
    }
}
