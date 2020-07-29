package com.yaozou.algorithm.leetcode.queue;

import java.util.ArrayList;
import java.util.List;

/**
 * created on 2020/7/29 17:58
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MyQueue {
    /** store element */
    private List<Integer> data;
    /** a pointer to indicate the start position */
    private int p_start;
    public MyQueue(){
        p_start = 0;
        data    = new ArrayList<>();
    }
    /** insert element to queue,return true if operation is successful*/
    public boolean enQueue(int x){
        data.add(x);
        return true;
    }

    /** delete element from the queue */
    public boolean deQueue(){
       if (isEmpty()){
           return false;
       }
       p_start++;
       return true;
    }

    /** Get the front item from the queue. */
    public int Front() {
        return data.get(p_start);
    }

    public boolean isEmpty(){
        return p_start >= data.size();
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.enQueue(5);
        queue.enQueue(3);

        if (!queue.isEmpty()){System.out.println(queue.Front());}
        queue.deQueue();
        if (!queue.isEmpty()){System.out.println(queue.Front());}
        queue.deQueue();
        if (!queue.isEmpty()){System.out.println(queue.Front());}
    }

}
