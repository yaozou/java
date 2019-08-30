package com.yaozou.jdk.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Auther: yaozou
 * @Date: 2018/9/17 10:16
 */
public class ThreadTest extends Thread {
    private static final AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        while (true)
            (new ThreadTest()).start();

    }

    @Override
    public void run() {
        System.out.println(count.incrementAndGet());
        while (true)
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                break;
            }
    }
}
