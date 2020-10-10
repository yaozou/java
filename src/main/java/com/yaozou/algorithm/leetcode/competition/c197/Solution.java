package com.yaozou.algorithm.leetcode.competition.c197;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
        List<List<Pair>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < edges.length; i++) {
            int[] e = edges[i];
            graph.get(e[0]).add(new Pair(succProb[i], e[1]));
            graph.get(e[1]).add(new Pair(succProb[i], e[0]));
        }

        PriorityQueue<Pair> que = new PriorityQueue<>();
        double[] prob = new double[n];

        que.offer(new Pair(1, start));
        prob[start] = 1;
        while (!que.isEmpty()) {
            Pair pair = que.poll();
            double pr = pair.probability;
            int node = pair.node;
            if (pr < prob[node]) {
                continue;
            }
            for (Pair pairNext : graph.get(node)) {
                double prNext = pairNext.probability;
                int nodeNext = pairNext.node;
                if (prob[nodeNext] < prob[node] * prNext) {
                    prob[nodeNext] = prob[node] * prNext;
                    que.offer(new Pair(prob[nodeNext], nodeNext));
                }
            }
        }
        return prob[end];
    }

    class Pair implements Comparable<Pair>{
        double probability;
        int    node;
        public Pair(double probability,int node){
            this.probability = probability;
            this.node        = node;
        }
        @Override
        public int compareTo(Pair o) {
            if (this.probability == o.probability) {
                return this.node - o.node;
            } else {
                return this.probability - o.probability > 0 ? -1 : 1;
            }
        }
    }

    public double getMinDistSum(int[][] positions) {
        if (positions.length <= 1){
            return 0D;
        }

        // 计算中心点，以中心点为圆心，中心到最远一点的距离为半径画圆，所有的点都在个圆点内
        // https://leetcode-cn.com/problems/best-position-for-a-service-centre/
    }
}
