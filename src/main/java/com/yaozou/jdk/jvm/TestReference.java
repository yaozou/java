package com.yaozou.jdk.jvm;

import java.io.File;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class TestReference {
    final int i = 0;
    static int j =0;

    public void reference(){
        TestReference jvm = new TestReference();
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
