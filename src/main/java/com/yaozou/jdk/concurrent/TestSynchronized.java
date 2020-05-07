package com.yaozou.jdk.concurrent;

class TestSynchronized{
    private static int count = 0;

    public static void main(String[] args) throws Exception{
        System.out.println("-----------一个对象多个线程------------");
        // 一个对象多个线程
        oneObjectMultipleThreads();

        System.out.println("-----------多个对象多个线程------------");
        // 多个对象多个线程
        multipleObjectsMultipleThreads();
    }


    private static void oneObjectMultipleThreads() throws Exception{
        TestSynchronized testSynchronized = new TestSynchronized();

        for (int n = 1;n<=5;n++){
            for (int i=0;i<100;i++){
                final int methodNum = n;
                new Thread(() -> {
                    try{
                        Thread.sleep(10);
                    }catch (Exception e){}
                    switch (methodNum){
                        case 1:
                            testSynchronized.method1();
                            break;
                        case 2:
                            testSynchronized.method2();
                            break;
                        case 3:
                            testSynchronized.method3();
                            break;
                        case 4:
                            testSynchronized.method4();
                            break;
                        case 5:
                            testSynchronized.method5();
                            break;
                        default:testSynchronized.method1();
                    }
                }).start();
            }

            Thread.sleep(1200);
            System.out.println("method"+n+" count:"+count);
            count = 0;
        }
    }

    private static void multipleObjectsMultipleThreads() throws Exception{
        for (int n = 1;n<=5;n++){
            for (int i=0;i<100;i++){
                final int methodNum = n;
                new Thread(() -> {
                    try{
                        Thread.sleep(10);
                    }catch (Exception e){}
                    TestSynchronized testSynchronized = new TestSynchronized();
                    switch (methodNum){
                        case 1:
                            testSynchronized.method1();
                            break;
                        case 2:
                            testSynchronized.method2();
                            break;
                        case 3:
                            testSynchronized.method3();
                            break;
                        case 4:
                            testSynchronized.method4();
                            break;
                        case 5:
                            testSynchronized.method5();
                            break;
                        default:testSynchronized.method1();
                    }
                }).start();
            }

            Thread.sleep(1200);
            System.out.println("method"+n+" count:"+count);
            count = 0;
        }
    }

    /**
     * 锁实例object对象
     */
    Object  lock = new Object();
    public void method1(){
        // 同步代码块，锁住的是配置的实例对象
        // Object作为锁
        synchronized (lock){
            TestSynchronized.count++;
        }
    }

    /**
     * 锁类的实例对象
     */
    public void method2(){
        // 同步代码块，锁住的是该类的实例对象
        synchronized (this){
            TestSynchronized.count++;
        }
    }

    /**
     * 锁类对象
     * 锁住的是该类的类对象
     */
    public void method3(){
        synchronized(TestSynchronized.class){
            TestSynchronized.count++;
        }
    }


    /**
     * 锁类的实例对象
     * 实例方法，锁住的是该类的实例对象
     */
    public synchronized void method4(){
        TestSynchronized.count++;
    }

    /**
     * 锁类对象
     * 静态方法，锁住的是类对象
     */
    public static synchronized void method5(){
        TestSynchronized.count++;
    }
}

