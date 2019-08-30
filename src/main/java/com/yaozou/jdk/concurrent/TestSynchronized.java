package com.yaozou.jdk.concurrent;

class TestSynchronized{
    public static void main(String[] args){
        synchronized (TestSynchronized.class){

        }
        System.out.println("over");
    }
    /**
     * 锁实例object对象
     */
    public void method1(){
        Object  lock = new Object();
        // 同步代码块，锁住的是配置的实例对象
        // Object作为锁
        synchronized (lock){
            System.out.println("----method1----");
        }
    }

    /**
     * 锁类的实例对象
     */
    public void method2(){
        // 同步代码块，锁住的是该类的实例对象
        synchronized (this){
            System.out.println("----method2----");
        }
    }

    /**
     * 锁类对象
     * 锁住的是该类的类对象
     */
    public void method3(){
        synchronized(TestSynchronized.class){
            System.out.println("----method3----");
        }
    }


    /**
     * 锁类的实例对象
     * 实例方法，锁住的是该类的实例对象
     */
    public synchronized void method4(){
        System.out.println("----method4----");
    }

    /**
     * 锁类对象
     * 静态方法，锁住的是类对象
     */
    public static synchronized void method5(){
        System.out.println("----method5----");
    }
}

