package com.yaozou.algorithm.leetcode.competition.c197;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

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
        // 题解析
        // 文档：2-梯度下降.note
        // 链接：http://note.youdao.com/noteshare?id=6ba5d61ac852dc10c3a79ea000e9e9cb&sub=B8B4429B054C404F8807E752F37BF706
        if (positions.length <= 1){
            return 0D;
        }

        double alpha = 1,eps=1e-7,decay=1e-3;
        int n = positions.length,batchSize=n;

        // 1、获得初始点
        double x = 0.0,y = 0.0;
        for (int[] pos:positions){
            x += pos[0];y += pos[1];
        }
        x /= n;
        y /= n;


        // 2、梯度下降
        while (true){
            double xp = x,yp = y;
            // 数据洗牌，随意打乱
            shuffle(positions);

            // 得到新的点(dx,dy)，alpha并衰减
            for (int i = 0;i<n;i += batchSize){
                int j = Math.min(i+batchSize,n);
                double dx=0.0,dy=0.0;
                // 根据函数的导数计算新的点
                for (int k = i;k<j;k++){
                    int[] pos = positions[k];
                    dx += (x - pos[0]) / (Math.sqrt((x - pos[0]) * (x - pos[0]) + (y - pos[1]) * (y - pos[1])) + eps);
                    dy += (y - pos[1]) / (Math.sqrt((x - pos[0]) * (x - pos[0]) + (y - pos[1]) * (y - pos[1])) + eps);
                }

                x -= alpha*dx;
                y-= alpha*dy;

                // alpha衰减
                alpha *= (1.0-decay);
            }

            // 当(xs,ys)与(yp,yp)的距离小于10的-7次方时结束迭代。
            if (Math.sqrt((x-xp)*(x-xp)+(y-yp)*(y-yp)) < eps){
                break;
            }
        }

        // 3、计算距离
        return distance(x,y,positions);
    }

    private void shuffle(int[][] positions){
        Random r = new Random();
        int size = positions.length;
        for (int i = 0;i<size;i++){
            int index = r.nextInt(size);
            int x = positions[i][0],y=positions[i][1];
            positions[i][0] = positions[index][0];
            positions[i][1] = positions[index][1];

            positions[index][0] = x;
            positions[index][1] = y;
        }
    }

    private double distance(double x , double y,int[][] positions){
        double distance = 0;
        for (int[] pos:positions) {
            distance += Math.sqrt((pos[0]-x)*(pos[0]-x)+(pos[1]-y)*(pos[1]-y));
        }
        System.out.println(x+","+y);
        return distance;
    }
}
