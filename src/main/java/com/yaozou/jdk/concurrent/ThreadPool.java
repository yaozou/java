package com.yaozou.jdk.concurrent;

import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池
 */
public class ThreadPool {
    int corePoolSize;
    int maximumPoolSize;
    long keepAliveTime;
    TimeUnit unit;
    BlockingQueue<Runnable> workQueue;
    ThreadFactory threadFactory;
    RejectedExecutionHandler handler;

    /**
     *
     * @param corePoolSize  核心池大小 默认情况下，在创建好线程池之后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
     *                      当线程池中的线程数量达到corePoolSize后，就会把这些任务放到缓存队列中
     * @param maximumPoolSize 线程池最大线程数量，表示在线程池中最多能创建线程的数量；在corePoolSize和maximumPoolSize的线程数会被自动释放，而小于corePoolSize的则不会。
     * @param keepAliveTime 等待工作的空闲线程的超时时间(纳秒)。
     *                      当存在超过corePoolSize或allowCoreThreadTimeOut(为true)时，线程将使用此超时。否则，他们将永远等待新的工作。
     * @param unit 参数keepAliveTime的时间单位，一个时间单位枚举类。 Nanos/Micros/Millis/Seconds/Minutes/Hours/Days
     * @param workQueue 一个阻塞队列，用来存储等待执行任务的队列，这个参数选择也很重要，会对线程池的运行过程产生重大影响。
     *                  一般来说，这里的阻塞队列就是（ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue）。
     * @param threadFactory 线程工厂，主要用来创建线程；可以是一个自定义的线程工厂，默认就是Executors.defaultThreadFactory()。用来在线程池中创建线程。
     * @param handler 表示当拒绝处理任务时的策略，也是可以自定义的，默认是我们前面的4种取值：
     *                  ThreadPoolExecutor.AbortPolicy（默认的，一言不合即抛异常的）
     *                  ThreadPoolExecutor.DiscardPolicy（一言不合就丢弃任务）
     *                  ThreadPoolExecutor.DiscardOldestPolicy（一言不合就把最近的任务给抛弃，然后执行当前任务）
     *                  ThreadPoolExecutor.CallerRunsPolicy（由调用者所在线程来执行任务）
     */
    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        if (corePoolSize < 0 || maximumPoolSize <= 0 || maximumPoolSize < corePoolSize || keepAliveTime < 0)
        {
            throw new IllegalArgumentException();
        }
        if (workQueue == null || threadFactory == null || handler == null)
        {
            throw new NullPointerException();
        }
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
}

class JdkThreadPool{
    public static void main(String[] args){
        // 有界new ArrayBlockingQueue<>
        // corePoolSize:1 maximumPoolSize:2 workQueue:6 创建了2个线程  maxSize:8
        // corePoolSize:1 maximumPoolSize:4 workQueue:6 创建了4个线程  maxSize:10
        // corePoolSize:1 maximumPoolSize:20 workQueue:6 创建了20个线程 maxSize:26

        // 无界 new LinkedBlockingQueue<>()


        ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(1, 3,
                1L, TimeUnit.MILLISECONDS,
                 new ArrayBlockingQueue<>(2));
        /*fixedThreadPool.allowCoreThreadTimeOut(true);*/
        int n = 0;
        int maxSize = 5;
        while ((n++ < maxSize)){
            fixedThreadPool.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
        }

        long start = System.currentTimeMillis();
        while (true){
            if (System.currentTimeMillis() - start > 10000){
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            System.out.println(fixedThreadPool.toString());
        }

        // 仅执行之前提交的任务，已经执行了shutdown之后不再接受新提交的任务。
        // 不保证已经提交的任务执行成功
        /*fixedThreadPool.shutdown();*/
        // 试图停止所有正在执行的任务
        // 清空任务队列
        // 返回所有等待执行的任务
        /*List<Runnable> list = fixedThreadPool.shutdownNow();*/
        // 等待所有任务执行成功
        /*try {
            boolean flag = fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }*/
    }
}
