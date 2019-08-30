package com.yaozou.jdk.concurrent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
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

/**
 * 信号量Semaphore
 * 设置信号量的初始值为等待的线程数N，一开始将信号量申请完，
 * 让剩余的信号量为0，待事件发生时，同时释放N个占用的信号量，
 * 则等待信号量的所有线程将获取信号量得以继续执行。
 */
class TestSemaphore{
    public static void main(String[] args){
        // 8位工人
        int n = 8;
        // 5台机器
        Semaphore semaphore = new Semaphore(5);
        for(int i=0;i<n;i++){
            new Worker(i,semaphore).start();
        }
    }
    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人"+this.num+"占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人"+this.num+"释放出机器");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 闭锁CountDownLatch
 * 将闭锁的初始值设置1，所有线程调用await方法等待，当事件发生时调用countDown将闭锁值减为0，
 * 则所有await等待闭锁的线程得以继续执行。
 */
class TestCountDownLatch{
    public static void main(String[] args){
        int num = 2;
        final CountDownLatch latch = new CountDownLatch(num);
        for(;num> 0;num--){
            new Thread(){
                public void run() {
                    try {
                        System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                        Thread.sleep(3000);
                        System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 栅栏CyclicBarrier
 * 设置栅栏的初始值为1，当事件发生时，调用barrier.wait()冲破设置的栅栏，
 * 将调用指定的Runable线程执行，在该线程中启动N个新的子线程执行。
 * 这个方法并不是让执行中的线程全部等待在某个点，待某一事件发生后继续执行。
 */
class TestCyclicBarrier{
    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++){
            new Writer(barrier).start();
        }
    }
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                //以睡眠来模拟写入数据操作
                Thread.sleep(5000);
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }

            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}


