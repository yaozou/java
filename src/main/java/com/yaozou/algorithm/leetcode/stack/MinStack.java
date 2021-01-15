package com.yaozou.algorithm.leetcode.stack;

import java.util.*;

/**
 * created on 2020/7/30 11:47
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MinStack {
    Stack<Integer> data,minStack;
    /** initialize your data structure here. */
    public MinStack() {
        data = new Stack<>();
        minStack = new Stack<>();
        minStack.add(Integer.MAX_VALUE);
    }

    public void push(int x) {
        data.add(x);
        minStack.add(Math.min(minStack.peek(),x));
    }

    public void pop() {
        data.pop();
        minStack.pop();
    }

    public int top() {
        return data.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

}
