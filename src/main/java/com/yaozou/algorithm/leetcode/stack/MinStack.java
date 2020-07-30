package com.yaozou.algorithm.leetcode.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * created on 2020/7/30 11:47
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MinStack {
    List<Integer> data;
    int min;
    /** initialize your data structure here. */
    public MinStack() {
        data = new ArrayList<>();
    }

    public void push(int x) {
        if (data.size() == 0 || min > x){min = x;}
        data.add(x);
    }

    public void pop() {
        int val = data.remove(data.size() -1);
        if (val == min){
            if(data.size() > 0){
                min = data.get(0);
                for (int i = 1;i<data.size();i++){
                    if (min > data.get(i)){
                        min = data.get(i);
                    }
                }
            }

        }
    }

    public int top() {
        return data.get(data.size() -1);
    }

    public int getMin() {

        return min;
    }
}
