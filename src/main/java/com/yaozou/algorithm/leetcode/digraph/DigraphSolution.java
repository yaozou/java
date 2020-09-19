package com.yaozou.algorithm.leetcode.digraph;

import java.util.HashMap;
import java.util.Map;

/**
 * created on 2020/9/17 17:43
 *
 * @author yaozou
 * @since v1.0.0
 */
public class DigraphSolution {
    public static void main(String[] args) {
        // [[2,1],[3,1],[4,2],[1,4]]        {{2,1},{3,1},{4,2},{1,4}} 2->1
        // [[1,2], [1,3], [2,3]]            {{1,2}, {1,3}, {2,3}}  2->3
        // [[1,2], [2,3],[3,4],[4,1],[1,5]] {{1,2}, {2,3},{3,4},{4,1},{1,5}}  4->1
        // [[1,2], [2,3], [3,1]]            {{1,2}, {2,3}, {3,1}} 3->1
        // [[4,2],[1,5],[5,2],[5,3],[2,4]]  {{4,2},{1,5},{5,2},{5,3},{2,4}} 4->2
        // [[4,2],[1,5],[5,2],[5,3],[1,4]]  {{4,2},{1,5},{5,2},{5,3},{1,4}} 5->2
        // [[1,4],[5,2],[1,3],[4,5],[1,5]]
       execute(new int[][]{{2,1},{3,1},{4,2},{1,4}});
       execute(new int[][]{{1,2}, {1,3}, {2,3}});
       execute(new int[][]{{1,2}, {2,3},{3,4},{4,1},{1,5}});
       execute(new int[][]{{1,2}, {2,3}, {3,1}});
        execute(new int[][]{{4,2},{1,5},{5,2},{5,3},{2,4}});
        execute(new int[][]{{4,2},{1,5},{5,2},{5,3},{1,4}});
    }

    private static void execute(int[][] edges){
        DigraphSolution solution = new DigraphSolution();
        int[] result = solution.findRedundantDirectedConnection(edges);
        System.out.println(result[0]+"->"+result[1]);
    }

    public int[] findRedundantDirectedConnection(int[][] edges) {
       int[] result = new int[2];

       // 仅存在一个子节点 父结点，子节点
       Map<Integer,Integer> m1 = new HashMap<>(edges.length);
       // 两个子节点       父结点，子节点
       Map<Integer,Integer> m2 = new HashMap<>(16);

       // 为一个父结点，一个子结点 子结点，父结点
        Map<Integer,Integer> m3 = new HashMap<>(edges.length);
       // 为一个父结点，两个子结点  子结点，父结点
        Map<Integer,Integer> m4 = new HashMap<>(16);
       for (int i=0;i<edges.length;i++){
           // key1->key2
           int key1 = edges[i][0];
           int key2 = edges[i][1];
            if (!m1.containsKey(key1)){
                m1.put(key1,key2);
            }else{
                m2.put(key1,key2);
            }

            if (!m3.containsKey(key2)){
                m3.put(key2,key1);
            }else{
                m4.put(key2,key1);
            }
       }

       if (!m2.isEmpty() && !m4.isEmpty()){
           // 为两个子节点的父结点       父结点，子节点
           // 父结点，子节点
           int[] dif1 = new int[2];
           for (Map.Entry<Integer, Integer> entry :m2.entrySet()) {
               dif1[0] = entry.getKey();
               dif1[1] = entry.getValue();
               break;
           }

           // 为一个父结点，两个子结点  子结点，父结点
           // 父结点，子节点
           int[] dif2 = new int[2];
           for (Map.Entry<Integer, Integer> entry :m4.entrySet()) {
               dif2[0] = entry.getValue();
               dif2[1] = entry.getKey();
               break;
           }

           if (m1.containsKey(dif2[1])){
               result[0] = m1.get(dif2[1]);
               result[1] = dif2[1];
               return result;
           }
           int end = dif2[1];
           // 跟key相关的，取最后一次出现的
           for (int i=0;i<edges.length;i++) {
               // key1->key2
               if (end == edges[i][1]){
                   result[0] = edges[i][0];
                   result[1] = end;
               }
           }
           return result;
       }else if (!m2.isEmpty()){
           // 为两个子节点的父结点       父结点，子节点
           // 父结点，子节点
           int[] dif = new int[2];
           for (Map.Entry<Integer, Integer> entry :m2.entrySet()) {
               dif[0] = entry.getKey();
               dif[1] = entry.getValue();
               break;
           }
           Integer n1 = m1.get(dif[0]);
           if (n1 != null && m1.get(n1).intValue() == dif[1]){
              result[0] = n1;
              result[1] = dif[1];
               return result;
           }

           /**
            * 5 <-  1 -> 2
            *      ^    |
            *      |    v
            *      4 <- 3
            */
           if (cricle1(dif,dif[0],m1)){
              int val =  m1.get(dif[0]);
              dif[1]  = val;
           }

           // 跟key相关的，取最后一次出现的
           for (int i=0;i<edges.length;i++) {
               // key1->key2
               int key1 = edges[i][0];
               int key2 = edges[i][1];
               if ((key1 == dif[0] || key2 == dif[0]) && (key2 != dif[1])){
                   result[0] = key1;
                   result[1] = key2;
               }
           }
       }else if (!m4.isEmpty()){
           // 为一个父结点，两个子结点  子结点，父结点
           // 父结点，子节点
           int[] dif = new int[2];
           for (Map.Entry<Integer, Integer> entry :m4.entrySet()) {
               dif[0] = entry.getValue();
               dif[1] = entry.getKey();
               break;
           }

           result[0] = m3.get(dif[1]);
           // TODO 父结点 取未形成闭环的结点
           /**
            *    1 ->5
            *  / ^
            * v   \
            * 2-->3
            */
           if (cricle1(dif,dif[0],m2)){
               result[0] = dif[0];
           }

           // 取子节点还有子节点的数组
           result[1] = dif[1];

       }else{
           result = edges[edges.length-1];
       }
       return result;
    }

    private boolean cricle1(int[] dif,int end,Map<Integer,Integer> m){
        //2<-1->3
        //2->1<-3
        boolean flag = true;

        int son = dif[1];
        Integer next = son;
        while (next!= null){
            next = m.get(son);
            if (next!=null && next.intValue() == end){
                break;
            }
        }

        if (next == null){
            flag = false;
        }

        return flag;
    }

}
