package com.yaozou.jdk;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.*;

/**
 * @Description: 并发
 * @Auther: yaozou
 * @Date: 2018/8/23 14:31
 */
public class TestConcurrent {
    private static Lock reentrantLock = new ReentrantLock();
    public static void testReentrantLock(){
        //lock
        reentrantLock.lock();
        try{

        }finally {
            reentrantLock.unlock();
        }
    }

    private static Map<String,String> map = new HashMap<>();
    private static ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = reentrantReadWriteLock.readLock();
    private static Lock writeLock = reentrantReadWriteLock.writeLock();
    public static void testReadLock(String key){
        readLock.lock();
        try{
            map.get(key);
        }finally {
            readLock.unlock();
        }
    }
    public static void testWriteLock(String key , String value){
        writeLock.lock();
        try{
            map.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }

    private double x, y;
    private final StampedLock sl = new StampedLock();
    void move(double deltaX, double deltaY) { // an exclusively locked method
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
         } finally {
            sl.unlockWrite(stamp);
         }
    }

    double distanceFromOrigin() { // A read-only and optimistic locked method
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            }finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    void moveIfAtOrigin(double newX, double newY) { // upgrade
        // Could instead start with optimistic, not read mode
        long stamp = sl.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                long ws = sl.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                 }
                else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
             }
        }finally {
            sl.unlock(stamp);
        }
    }
}
