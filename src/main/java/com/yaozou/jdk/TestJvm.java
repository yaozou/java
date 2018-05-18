package com.yaozou.jdk;/**
 * created by yaozou on 2018/5/3
 */

import java.io.File;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 *
 * @author yaozou
 * @create 2018-05-03 21:41
 **/
public class TestJvm {
    final int i = 0;
    static int j =0;

    public void reference(){
        TestJvm jvm = new TestJvm();
        // 强引用  是指创建一个对象并把这个对象赋给一个引用变量。
        Object object = new Object();
        Object[] objArr = new Object[1000];

        // 软引用
        SoftReference softReference = new SoftReference(jvm);

        // 弱引用
        WeakReference weakReference = new WeakReference(jvm);

        // 虚引用
        ReferenceQueue<String> queue = new ReferenceQueue<String>();
        PhantomReference<String> pr = new PhantomReference<String>(new String("hello"), queue);
        System.out.println(pr.get());
    }

    Object obj = new Object();
    public void method1(int i){
        int j = 0;
        int sum = i+j;
        Object abc = obj;
        long start = System.currentTimeMillis();

        return;// 异常 正常
    }
    public void method2(){
        File file = new File("");
    }
    public void method3(){
        method3();
    }
}
class TestGC{
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


