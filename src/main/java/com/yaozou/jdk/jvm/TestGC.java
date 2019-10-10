package com.yaozou.jdk.jvm;

public class TestGC {
    public static TestGC testGC = null;
    public static void main(String[] args) throws InterruptedException {
        testGC = new TestGC();
        testGC = null;
        System.gc();
        Thread.sleep(500);
        if (null != testGC) { //此时对象应该处于(reachable, finalized)状态
            System.out.println("Yes , I am still alive");
        } else {
            System.out.println("No , I am dead");
        }
        testGC = null;
        System.gc();
        Thread.sleep(500);
        if (null != testGC) {
            System.out.println("Yes , I am still alive");
        } else {
            System.out.println("No , I am dead");
        }
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("execute method finalize()");
        testGC = this;
    }
}
