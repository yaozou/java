package com.yaozou.algorithm.leetcode.stack;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

/**
 * created on 2021/1/11 15:53
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MyQueue {
    private Stack<Integer> stack;
    /** Initialize your data structure here. */
    public MyQueue() {
        stack = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        stack.add(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        int o = peek();
        stack.removeElementAt(0);
        return o;
    }

    /** Get the front element. */
    public int peek() {
       return stack.get(0);
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stack.empty();
    }

    class MyStack {
        private Queue<Integer> queueAsc;
        private Queue<Integer> queueDesc;

        /** Initialize your data structure here. */
        public MyStack() {
            queueAsc = new LinkedList<>();
            queueDesc = new LinkedList<>();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            queueAsc.add(x);
            int[] array = new int[queueAsc.size()];
            int i = 0;
            Iterator<Integer> iterator = queueAsc.iterator();
            while (iterator.hasNext()){
                array[i] = iterator.next();
                i++;
            }
            queueDesc.clear();
            for (i=array.length-1;i>=0;i--){
                queueDesc.add(array[i]);
            }
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
           int o = queueDesc.poll();
            int[] array = new int[queueDesc.size()];
            int i = 0;
            Iterator<Integer> iterator = queueDesc.iterator();
            while (iterator.hasNext()){
                array[i] = iterator.next();
                i++;
            }
            queueAsc.clear();
            for (i=array.length-1;i>=0;i--){
                queueAsc.add(array[i]);
            }
           return o;
        }

        /** Get the top element. */
        public int top() {
            return queueDesc.peek();
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return queueAsc.isEmpty() && queueDesc.isEmpty();
        }
    }
}



