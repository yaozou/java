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
    private int size;
    private int[] data;
    /** Initialize your data structure here. Set the size of the queue to be k. */
    public MyCircularQueue(int k) {
        head = -1;
        tail = -1;
        size = k;
        data = new int[k];
    }

    /** Insert an element into the circular queue. Return true if the operation is successful. */
    public boolean enQueue(int value) {
        if (isFull()){return false;}
        if (isEmpty()){
            head = 0;
        }
        tail = (tail+1)%size;
        data[tail] = value;
        return true;
    }

    /** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if (isEmpty()){return false;}
        if (head == tail){
            head = -1;tail=-1;
            return true;
        }
        head = (head+1)%size;
        return true;
    }

    /** Get the front item from the queue. */
    public int Front() {
        if (isEmpty()){return -1;}
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
        return head == -1 && tail == -1;
    }

    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
        return (tail+1)%size == head;
    }

    public static void main(String[] args) {
        // true,true,true,false,3,true,true,true,4
        test1();
        System.out.println();
        // true,6,6,true,true,5,true,-1,false,false,false
        test2();
        System.out.println();
        // [true,3,false,true,true,true,true,true,false,false,4]
        test3();
        System.out.println();
    }

    private static void test1(){
        MyCircularQueue circularQueue = new MyCircularQueue(3); // 设置长度为 3
        print(circularQueue.enQueue(1));  // 返回 true
        print(circularQueue.enQueue(2));  // 返回 true
        print(circularQueue.enQueue(3));  // 返回 true
        print(circularQueue.enQueue(4));  // 返回 false，队列已满


        print(circularQueue.Rear());  // 返回 3
        print(circularQueue.isFull());  // 返回 true

        print(circularQueue.deQueue());  // 返回 true

        print(circularQueue.enQueue(4));  // 返回 true

        print(circularQueue.Rear());  // 返回 4
    }

    private static void test2(){
        MyCircularQueue circularQueue = new MyCircularQueue(6); // 设置长度为 3
       print(circularQueue.enQueue(6));  // 返回 true

        print(circularQueue.Rear());  // 返回 6
        print(circularQueue.Rear());  // 返回 6

        print(circularQueue.deQueue());  // 返回 true
        print(circularQueue.enQueue(5));  // 返回 true

        print(circularQueue.Rear());  // 返回 5
        print(circularQueue.deQueue());  // 返回 true

        print(circularQueue.Front());  // 返回 -1
        print(circularQueue.deQueue());  // 返回 false
        print(circularQueue.deQueue());  // 返回 false
        print(circularQueue.deQueue());  // 返回 false
    }

    private static void test3(){
        MyCircularQueue circularQueue = new MyCircularQueue(4); // 设置长度为 3

        print(circularQueue.enQueue(3));  // 返回 true

        print(circularQueue.Front());  // 返回 3

        print(circularQueue.isFull());  // 返回 false

        print(circularQueue.enQueue(7));  // 返回 true
        print(circularQueue.enQueue(2));  // 返回 true
        print(circularQueue.enQueue(5));  // 返回 true

        print(circularQueue.deQueue());  // 返回 true
        print(circularQueue.enQueue(4));  // 返回 true
        print(circularQueue.enQueue(2));  // 返回 false

        print(circularQueue.isEmpty());  // 返回 false

        print(circularQueue.Rear());  // 返回 4
    }

    private static  void print(Object o){
        System.out.print(o+",");
    }
}
