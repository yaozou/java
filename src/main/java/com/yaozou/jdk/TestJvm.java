package com.yaozou.jdk;/**
 * created by yaozou on 2018/5/3
 */

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
}
