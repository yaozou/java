package com.yaozou.jdk.jvm;

/**
 * 1、深拷贝  如果对象中除了基本数据对象还有引用数据类型，那用clone()方法获取到的这个对象是浅拷贝。
 * 2、浅拷贝  如果对象中只有基本数据类型，那用clone()方法获取到的这个对象就是深拷贝。
 * @author yaozou
 */
public class TestClone implements Cloneable {

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
