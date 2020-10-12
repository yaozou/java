package com.yaozou.algorithm.leetcode.competition.c197;

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
        if (positions.length <= 1){
            return 0D;
        }
        // 计算一个中心点到各个点的距离最短
        // 给定的函数是一个凸函数
        // 凸优化问题
        // 局部最小值就等于全部最小值
        // 先求解局部最小值
        // 1、使用梯度下降法，求解局部最小值：对于给定的(x,y),它的梯度方向是函数值上升最快的方向，因此梯度反向就是函数值下降最快的方向，函数为：
        //      f(xc​,yc​)=i=0∑n−1​(xc​−xi​)2+(yc​−yi​)2
        //​ 求得它的导数为：
        //      f`(x)​
        //      f`(y)
        // 那么梯度反向=(-f`(x),f`(y))。从一个初始点(xs,ys)开始进行迭代 ，每次令
        //      xs`=xs-alpha*f`(x)
        //      ys`=ys-alpha*f`(y)
        // 得到新的点(xs`,ys`),alpha为学习率。
        // 当迭代了一定次数之后，当前的点会非常接近真正的最小值点，如果我们的学习速率不变，迭代的结果将会在最小值点的周围来回震荡，无法继续接近最小值点。因此，需要设置学习率衰减，在迭代的过程中逐渐减小学习率，向最小值点逼近。
        // 在代码中：
        //   初始点(xs,ys)=(x1+x2+....+xn)/n,(y1+y2+....+yn)/n
        //   学习率alpha=1
        //   学习率衰减n=10的-3次方
        //   当(xs,ys)与(xs`,ys`)的距离小于10的-7次方时结束迭代。

        double alpha = 1,eps=1e-7,decay=1e-3;
        int n = positions.length,batchSize=n;

        // 1、获得初始点
        double x = 0D,y = 0D;
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
            for (int i = 0;i<n;i++){
                int j = Math.min(i+batchSize,n);
                double dx=0D,dy=0D;
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
        double distance = 0D;
        for (int[] pos:positions) {
            double x2 = (pos[0]-x)*(pos[0]-x);
            double y2 = (pos[1]-y)*(pos[1]-y);
            distance += Math.sqrt(x2+y2);
        }
        return distance;
    }
}
