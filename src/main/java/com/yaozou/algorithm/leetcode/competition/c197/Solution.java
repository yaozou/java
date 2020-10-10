package com.yaozou.algorithm.leetcode.competition.c197;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created on 2020/10/10 9:24
 *
 * @author yaozou
 * @since v1.0.0
 */
public class Solution {

    public int numIdenticalPairs(int[] nums) {
        int num = 0;
        for (int i =0;i<nums.length-1;i++){
            for (int j =i+1;j<nums.length;j++){
                if (nums[i] == nums[j]){
                    num++;
                }
            }
        }
        return num;
    }


    public int numSub(String s) {
        if (s == null){return 0;}
        long num = 0,conNum = 0;
        char[] chars = s.toCharArray();
        char c = '1';
        for (int i = 0 ;i<chars.length;i++){
            boolean flag = c == chars[i];
            if (flag){
                num = num+1+conNum;
                conNum++;
            }else{
                conNum = 0;
            }
        }
        return (int)(num %((int)Math.pow(10,9) + 7));
    }

    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        return 0D;
    }

}
