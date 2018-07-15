package com.yaozou.thread;

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
     * @param keepAliveTime 表示线程没有执行任务时最多保持多久时间会终止。
     *                      默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会生效,直到线程池数量不大于corePoolSize，
     *                      即只有当线程池数量大于corePoolSize数量，超出这个数量的线程一旦到达keepAliveTime就会终止。
     *                      但是如果调用了allowCoreThreadTimeout(boolean)方法，即使线程池的线程数量不大于corePoolSize，线程也会在keepAliveTime之后就终止，知道线程池的数量为0为止。
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
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
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
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        ExecutorService workStealingPool = Executors.newWorkStealingPool();
    }
}
