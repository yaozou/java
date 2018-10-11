package com.yaozou.jdk;

import java.util.concurrent.*;

/**
 * @Description:
 * @Auther: yaozou
 * @Date: 2018/10/10 17:17
 */
public class TestQueue {
    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        boolean flag;
        Object val;
        // 由数组结构组成的有界阻塞队列
        BlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(10);
        // 队未满时，返回true，队满时则抛出异常IllegalStateException
        flag = arrayBlockingQueue.add(obj);
        // 队未满时，返回true，队满时返回false。非阻塞立即返回
        arrayBlockingQueue.offer(obj);
        // 队未满时直接插入无返回值，队满时阻塞等待，一直等到队列未满时再插入
        arrayBlockingQueue.put(obj);

        // 队列不为空时，返回队首值并移除。队为空时抛出异常NoSuchElementException
        val = arrayBlockingQueue.remove();
        // 队列不为空时，返回队首值并移除。队为空时返回null。非阻塞立即返回
        val = arrayBlockingQueue.poll();
        // 设定等待的时间，如果在指定时间内队列为空则返回null，不为空则返回队首值
        val =  arrayBlockingQueue.poll(1,TimeUnit.MINUTES);
        // 队列不为空返回队首值并移除；当队列为空时会阻塞等待，一直等到队列不为空时再返回队首值。
        val = arrayBlockingQueue.take();

        flag = arrayBlockingQueue.contains(obj);

        // 由链表结构组成的有界阻塞队列
        BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(10);
        // 支持优先级别排序的无界阻塞队列 默认大小为11
        BlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();
        // 使用有限级队列实现的无界阻塞队列 PriorityQueue
        BlockingQueue delayQueue = new DelayQueue();
        // 不存储元素的阻塞队列
        BlockingQueue synchronousQueue = new SynchronousQueue();
        // 由链表结构组成的无界阻塞队列
        BlockingQueue linkedTransferQueue = new LinkedTransferQueue();
        // 由链表结构组成的双阻塞队列
        BlockingQueue linkedBlockingDeque = new LinkedBlockingDeque();
    }
}
