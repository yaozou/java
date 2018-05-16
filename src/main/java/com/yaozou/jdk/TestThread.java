package com.yaozou.jdk;

import java.util.concurrent.Semaphore;

public class TestThread {

}

class TestSynchronized{
    Object lock = null;
    public void method1(){
        lock = new Object();
        synchronized (lock){
            System.out.println("----method1----");
        }
    }
    public void method2(){
        synchronized (this){
            System.out.println("----method2----");
        }
    }
    public synchronized void method3(){
        System.out.println("----method3----");
    }
    public static synchronized void method4(){
        System.out.println("----method4----");
    }
}

class TestSemaphore{
    Semaphore semaphore = new Semaphore(10);//控制10个共享资源的使用
    public void use(){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public  void  release(){
        semaphore.release();
    }
}

class TestThreadLocal{
    private static TestThreadLocal testThreadLocal = null;
    private static ThreadLocal<TestThreadLocal> map = new ThreadLocal<TestThreadLocal>();
    private TestThreadLocal(){}
    public static synchronized TestThreadLocal getInstance1(){
        if(testThreadLocal==null){
            testThreadLocal = new TestThreadLocal();
        }
        return testThreadLocal;
    }
    public static TestThreadLocal getInstance2(){ //效率大于getInstance1()
        testThreadLocal = map.get();
        if(testThreadLocal==null){
            testThreadLocal = new TestThreadLocal();
            map.set(testThreadLocal);
        }
        return testThreadLocal;
    }
}
