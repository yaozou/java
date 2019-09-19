package com.yaozou.jdk.concurrent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * @Description: TODO
 * @Author yao.zou
 * @Date 2019/9/18 0018
 * @Version V1.0
 **/
class TestLock{
    private static Lock reentrantLock = new ReentrantLock();
    public static void testReentrantLock(){
        try{
            // 获得锁 如果未获得当前锁，当前线程进入禁用调度目录并休眠，直到获得锁。
            reentrantLock.lock();
            // 获得锁时返回true , 未获得锁时返回false
            boolean tryLock1 = reentrantLock.tryLock();
            // 获得锁时返回true , 未获得锁时进入禁用调度目录并休眠，在三种情况下结束：
            //  1、获得了锁
            //  2、其他线程终端了此线程
            //  3、超过制定的等待时间
            boolean tryLock2 = reentrantLock.tryLock(100,TimeUnit.SECONDS);
            // 未获得锁时进入禁止调度目录并休眠，在两种情况下结束：
            //  1、获得了锁
            //  2、其他线程终端了此线程
            // 在当前线程可获得锁时，线程被中断，抛出InterruptedException异常。
            reentrantLock.lockInterruptibly();
            // 获得Condition之前此线程必须获得锁。
            Condition condition = reentrantLock.newCondition();
            testCondition();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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

    static void testLockSupport(){
        // 调用native方法阻塞当前线程
        LockSupport.park();
        // 阻塞当前线程，最长不超过nanos纳秒，返回条件在park()的基础上增加了超时返回
        LockSupport.parkNanos(1);
        // 阻塞当前线程，知道deadline时间（deadline - 毫秒数）
        LockSupport.parkUntil(1);

        Object blocker = new Object();
        //1) 记录当前线程等待的对象（阻塞对象）；
        //2) 阻塞当前线程；
        //3) 当前线程等待对象置为null。
        LockSupport.park(blocker);
        // 阻塞当前线程，最长等待时间不超过nanos毫秒，同样，在阻塞当前线程的时候做了记录当前线程等待的对象操作
        LockSupport.parkNanos(blocker,1);
        // 阻塞当前线程直到deadline时间，相同的，也做了阻塞前记录当前线程等待对象的操作
        LockSupport.parkUntil(blocker,1);

        // 唤醒处于阻塞状态的线程Thread
        LockSupport.unpark(new Thread());
    }

    static void testCondition() throws InterruptedException {
        // 需要结合lock使用
        Lock lock = new ReentrantLock();
        final Condition notEmpty = lock.newCondition();

        // 将当前线程阻塞，调用await()之前必须先获取锁，
        // 调用await()时，将线程构造成节点加入等待队列，同时释放锁，并挂起当前线程
        notEmpty.await();
        notEmpty.await(1, TimeUnit.MINUTES);
        notEmpty.awaitNanos(1);
        notEmpty.awaitUninterruptibly();
        notEmpty.awaitUntil(new Date());

        // 另一个线程将已经阻塞的线程唤醒
        // 其他线程调用signal()时也必须获取锁，
        // 当执行signal()方法时将等待队列的节点移入到同步队列，
        // 当线程退出临界区释放锁时，唤醒同步队列的首个节点
        notEmpty.signal();
        notEmpty.signalAll();
    }
}
