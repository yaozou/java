package com.yaozou.algorithm.leetcode.stack;

/**
 * created on 2020/7/30 11:38
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MyStack {
    private int[] data;
    private int size;
    private int tail;

    public MyStack(int x){
        data = new int[x];
        size = x;
        tail = -1;
    }
    /** Insert an element into the stack. */
    public void push(int x) {
        tail = (tail+1)%size;
        data[tail] = x;
    }
    /** Checks whether the queue is empty or not. */
    public boolean isEmpty() {
        return tail == -1;
    }
    /** Get the top item from the queue. */
    public int top() {
        if (isEmpty()){return -1;}
        return data[tail];
    }
    /** Delete an element from the queue. Return true if the operation is successful. */
    public boolean pop() {
        if (isEmpty()) {
            return false;
        }
        tail--;
        return true;
    }
}
