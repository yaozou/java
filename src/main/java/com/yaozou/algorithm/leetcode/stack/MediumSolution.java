package com.yaozou.algorithm.leetcode.stack;

import java.util.HashMap;
import java.util.Map;

/**
 * created on 2021/1/15 14:54
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MediumSolution {
    /** 移除最多的同行或同列石头 */
    public int removeStones(int[][] stones) {
        UnionFind unionFind = new UnionFind();
        for (int[] stone:stones){
            unionFind.union(stone[0] + 10001, stone[1]);
        }
        return stones.length-unionFind.count;
    }
    private class UnionFind{
        // key为行时，value为列
        // key为列时，value为列
        private Map<Integer,Integer> parent;
        private int count;
        public UnionFind() {
            this.parent = new HashMap<>();
            this.count = 0;
        }
        public int find(int x){
            if (!parent.containsKey(x)){
                parent.put(x,x);
                count++;
            }
            if (x != parent.get(x)){
                parent.put(x,find(parent.get(x)));
            }
            return parent.get(x);
        }

        public void union(int x,int y){
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY){return;}
            parent.put(rootX,rootY);
            count--;
        }
    }

    public static void main(String[] args) {
        MediumSolution solution = new MediumSolution();
        // {1,0},{0,1},{1,1}
        // {0,0},{0,1},{1,0},{1,2},{2,1},{2,2}
        System.out.println(solution.removeStones(new int[][]{{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}}));
    }
}
