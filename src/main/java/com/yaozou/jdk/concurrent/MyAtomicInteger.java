package com.yaozou.jdk.concurrent;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;

public class MyAtomicInteger extends Number implements Serializable {
    private static final Unsafe unsafe;
    private static final long   valueoffset;
    private volatile int value;
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
            Field valueField = MyAtomicInteger.class.getDeclaredField("value");
            valueoffset = unsafe.objectFieldOffset(valueField);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public MyAtomicInteger(){}
    public MyAtomicInteger(int initialValue){
        this.value = initialValue;
    }

    public final int get() {
        return value;
    }

    public final void set(int newValue){
        value = newValue;
    }

    public final void lazySet(int newValue){
        unsafe.putOrderedInt(this,valueoffset,newValue);
    }

    public final int getAndSet(int newValue){
        return unsafe.getAndSetInt(this,valueoffset,newValue);
    }

    public final boolean compareAndSet(int expect,int update){
        return unsafe.compareAndSwapInt(this,valueoffset,expect,update);
    }

    public final boolean weakCompareAndSet(int expect,int update){
        return unsafe.compareAndSwapInt(this,valueoffset,expect,update);
    }

    public final int getAndIncrement(){
        return unsafe.getAndAddInt(this,valueoffset,1);
    }

    public final int getAndDecrement(){
        return unsafe.getAndAddInt(this,valueoffset,-1);
    }

    public final int getAndAdd(int delta){
        return unsafe.getAndAddInt(this,valueoffset,delta);
    }

    public final int incrementAndGet(){
        return unsafe.getAndAddInt(this,valueoffset,1)+1;
    }

    public final int decrementAndGet(){
        return unsafe.getAndAddInt(this,valueoffset,-1)-1;
    }

    public final int addAndGet(int delta){
        return unsafe.getAndAddInt(this,valueoffset,delta)+1;
    }

    @Override
    public int intValue() {
        return get();
    }

    @Override
    public long longValue() {
        return get();
    }

    @Override
    public float floatValue() {
        return get();
    }

    @Override
    public double doubleValue() {
        return get();
    }
}
