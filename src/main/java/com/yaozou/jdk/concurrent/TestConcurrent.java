package com.yaozou.jdk.concurrent;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * @Description: 并发
 * @Auther: yaozou
 * @Date: 2018/8/23 14:31
 */
public class TestConcurrent {

    public static void main(String[] args){
        BlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(10);
        BlockingQueue delayQueue         = new DelayQueue();
        BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        BlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();
        BlockingQueue synchronousQueue = new SynchronousQueue();

        // TransferQueue extend BlockingQueue
        TransferQueue linkedTransferQueue   = new LinkedTransferQueue();


        BlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();

        Queue  concurrentLinkedQueue = new ConcurrentLinkedQueue();

        Deque  deque = new ConcurrentLinkedDeque();

        Map<String,Integer> map1 = new ConcurrentHashMap<>(16);
        map1.put("1111",1);
        ConcurrentNavigableMap<String,Integer> map2 = new ConcurrentSkipListMap<>();

        /** 由ConcurrentSkipListMap实现 */
        ConcurrentSkipListSet<Integer> set1 = new ConcurrentSkipListSet<>();
        set1.add(1); //添加
        set1.pollLast(); // 集合尾部推出
        set1.pollFirst(); // 集合头部推出

        CopyOnWriteArraySet<Integer>   set2 = new CopyOnWriteArraySet<>();


        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

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

class TestBlockingQueue{
    public static void main(String[] args){
        int n = 2;
        BlockingQueue<Message> queue = new ArrayBlockingQueue(n);

    }

    static class Message{
        private String msg;
        public Message(String msg){
            this.msg = msg;
        }

        @Override
        public String toString() {
            return this.msg;
        }
    }

   static class Producer extends Thread{
        private BlockingQueue<Message> queue;
        public Producer(BlockingQueue<Message> queue){
            this.queue = queue;
        }
       @Override
       public void run() {
           try {
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }

   static class Consumer extends Thread{
       private BlockingQueue<Message> queue;
       public Consumer(BlockingQueue<Message> queue){
           this.queue = queue;
       }
       @Override
       public void run() {

       }
   }
}


