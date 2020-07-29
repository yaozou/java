package com.yaozou.algorithm.leetcode.queue;

/**
 * created on 2020/7/29 18:12
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MyCircularQueue {
    private int head;
    private int tail;
    private int[] data;
    /** Initialize your data structure here. Set the size of the queue to be k. */
    public MyCircularQueue(int k) {
        head = -1;
        tail = -1;
        data = new int[k];
    }

    /** Insert an element into the circular queue. Return true if the operation is successful. */
    public boolean enQueue(int value) {
        if (isFull()){return false;}
        tail ++;
        if (tail == data.length){tail = 0;}
        data[tail] = value;
        return true;
    }

    /** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if (isEmpty()){return false;}
        head++;
        return true;
    }

    /** Get the front item from the queue. */
    public int Front() {
        return data[head];
    }

    /** Get the last item from the queue. */
    public int Rear() {
        if (isEmpty()){
            return -1;
        }
        return data[tail];
    }

    /** Checks whether the circular queue is empty or not. */
    public boolean isEmpty() {
        return head == -1&& -1 == tail;
    }

    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
        return tail-head == data.length;
    }

    public static void main(String[] args) {
        MyCircularQueue circularQueue = new MyCircularQueue(3); // 设置长度为 3
        System.out.println(circularQueue.enQueue(1));  // 返回 true
        System.out.println(circularQueue.enQueue(2));  // 返回 true
        System.out.println(circularQueue.enQueue(3));  // 返回 true
        System.out.println(circularQueue.enQueue(4));  // 返回 false，队列已满


        System.out.println(circularQueue.Rear());  // 返回 3
        System.out.println(circularQueue.isFull());  // 返回 true

        System.out.println(circularQueue.deQueue());  // 返回 true

        System.out.println(circularQueue.enQueue(4));  // 返回 true

        System.out.println(circularQueue.Rear());  // 返回 4
    }
}
