package com.yaozou.algorithm.leetcode.digraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created on 2020/9/17 17:43
 *
 * @author yaozou
 * @since v1.0.0
 */
public class DigraphSolution {
    public static void main(String[] args) {
        // (1)即是子节点也是两个节点的父结点
        //（2） 即是父结点也是两个节点的子节点
        //（3）有两个节点异常，既有(1)也有(2)，并且（1）情况的节点是三个节点的父结点
        //（4）有两个节点异常，既有(1)也有(2), 为（2）情况的节点它的一个子节点也是它的父结点
        // (5) (4)和(3)
        // (6) 有两个节点异常，既有(1)也有(2),并且（1）情况的节点也是一个子节点
        // (7) 有三个节点异常，既两个有(1)也一个有(2),并且（1）情况的一个节点也是一个子节点

        // [[1,2], [2,3],[3,4],[4,1],[1,5]] {{1,2}, {2,3},{3,4},{4,1},{1,5}}       4->1     (1)

        // [[2,1],[3,1],[4,2],[1,4]]        {{2,1},{3,1},{4,2},{1,4}}              2->1     (2)
        // [[1,2], [1,3], [2,3]]            {{1,2}, {1,3}, {2,3}}                  2->3     (2)
        // [[4,2],[1,5],[5,2],[5,3],[1,4]]  {{4,2},{1,5},{5,2},{5,3},{1,4}}        5->2     (2)
        // [[1,5],[3,2],[2,4],[4,5],[5,3]]  {{1,5},{3,2},{2,4},{4,5},{5,3}}        4->5     (2)

        // [[1,4],[5,2],[1,3],[4,5],[1,5]]  {{1,4},{5,2},{1,3},{4,5},{1,5}}        1->5     (3)
        // [[4,2],[1,5],[5,2],[4,3],[4,1]]   {{4,2},{1,5},{5,2},{4,3},{4,1}}       5->2     (3)
        // [[5,2],[1,5],[4,2],[4,3],[4,1]]   {{5,2},{1,5},{4,2},{4,3},{4,1}}       4->2     (3)

        // [[4,2],[1,5],[5,2],[5,3],[2,4]]  {{4,2},{1,5},{5,2},{5,3},{2,4}}        4->2     (4)
        // [[3,5],[1,3],[2,1],[5,4],[2,3]]   {{3,5},{1,3},{2,1},{5,4},{2,3}}       2->3     (4)

        // [[4,1],[1,5],[4,2],[5,1],[4,3]]   {{4,1},{1,5},{4,2},{5,1},{4,3}}       5->1     (5)

        // [[9,3],[4,9],[3,1],[10,2],[5,4],[6,8],[6,5],[10,6],[2,3],[3,7]]   {{9,3},{4,9},{3,1},{10,2},{5,4},{6,8},{6,5},{10,6},{2,3},{3,7}}       2->3

        // [[1,2], [2,3], [3,1]]            {{1,2}, {2,3}, {3,1}} 3->1


       /* System.out.println("-----------------(1)即是子节点也是两个节点的父结点----------------");
        execute(new int[][]{{1,2}, {2,3},{3,4},{4,1},{1,5}},"4->1");*/

        System.out.println("-----------------（2） 即是父结点也是两个节点的子节点----------------");
        execute(new int[][]{{2,1},{3,1},{4,2},{1,4}},"2->1","{{2,1},{3,1},{4,2},{1,4}}");
        /*execute(new int[][]{{1,2}, {1,3}, {2,3}},"2->3");
        execute(new int[][]{{4,2},{1,5},{5,2},{5,3},{1,4}},"5->2");
        execute(new int[][]{{1,5},{3,2},{2,4},{4,5},{5,3}},"4->5");*/

       /* System.out.println("-----------------（3）有两个节点异常，既有(1)也有(2)，并且（1）情况的节点是三个节点的父结点----------------");
        execute(new int[][]{{1,4},{5,2},{1,3},{4,5},{1,5}},"1->5");
        execute(new int[][]{{4,2},{1,5},{5,2},{4,3},{4,1}},"5->2");
        execute(new int[][]{{5,2},{1,5},{4,2},{4,3},{4,1}},"4->2");

        System.out.println("-----------------（4）有两个节点异常，既有(1)也有(2), 为（2）情况的节点它的一个子节点也是它的父结点----------------");
        execute(new int[][]{{4,2},{1,5},{5,2},{5,3},{2,4}},"4->2");
        execute(new int[][]{{3,5},{1,3},{2,1},{5,4},{2,3}},"2->3");

        System.out.println("-----------------(5) (4)和(3)----------------");
        execute(new int[][]{{4,1},{1,5},{4,2},{5,1},{4,3}},"5->1");

        System.out.println("-----------------other----------------");
       execute(new int[][]{{1,2}, {2,3}, {3,1}},"3->1");*/
    }

    private static void execute(int[][] edges,String str,String array){
        DigraphSolution solution = new DigraphSolution();
        int[] result = solution.findRedundantDirectedConnection(edges);
        String reStr = +result[0]+"->"+result[1];
        System.out.println(array+"  "+str+"  "+reStr+"->"+reStr.equals(str));
    }

    public int[] findRedundantDirectedConnection(int[][] edges) {
       // 仅存在一个子节点 父结点，子节点
       Map<Integer,Integer> m1 = new HashMap<>(edges.length);
       // 两个子节点       父结点，子节点
       Map<Integer,List<Integer>> m2 = new HashMap<>(16);

       // 为一个父结点，一个子结点 子结点，父结点
        Map<Integer,Integer> m3 = new HashMap<>(edges.length);
       // 为一个父结点，两个子结点  子结点，父结点
        Map<Integer,List<Integer>> m4 = new HashMap<>(16);

       for (int i=0;i<edges.length;i++){
           // key1->key2
           int key1 = edges[i][0];
           int key2 = edges[i][1];

           if (!m1.containsKey(key1)){
                m1.put(key1,key2);
            }else{
               List<Integer> list = m2.getOrDefault(key1,new ArrayList<>());
               list.add(key2);
                m2.put(key1,list);
            }

            if (!m3.containsKey(key2)){
                m3.put(key2,key1);
            }else{
                List<Integer> list = m4.getOrDefault(key1,new ArrayList<>());
                list.add(key1);
                m4.put(key2,list);
            }
       }

        if (!m4.isEmpty()){
            // 两个节点的子节点
            return result1(edges,m1,m2,m3,m4);
        }

       if (!m2.isEmpty()){
           // 多个节点的父结点
           return result2(edges,m1,m2,m3,m4);
       }
       return  edges[edges.length-1];
    }

    private int[] result1(int[][] edges,Map<Integer,Integer> m1,Map<Integer,List<Integer>> m2,Map<Integer,Integer> m3,Map<Integer,List<Integer>> m4){
        int[] result = new int[2];
        for (Map.Entry<Integer, List<Integer>> entry :m4.entrySet()) {
            int son = entry.getKey();
            List<Integer> list = entry.getValue();
            list.add(m3.get(son));

            Map<Integer,Boolean> circle = new HashMap<>(16);
            Map<Integer,Boolean> notCircle = new HashMap<>(16);
            for (Integer root:list) {
                if (cricle1(new int[]{root,son},root,m1)){
                    circle.put(root,true);
                }else{
                    notCircle.put(root,true);
                }
            }


            if (circle.size() == 1){
                for (Integer root: circle.keySet()) {
                    result[0] = root;
                    break;
                }
                result[1] = son;
                return result;
            }else{
                boolean isEmpty = circle.isEmpty();
                for (int i=0;i<edges.length;i++) {
                    // key1->key2
                    int key1 = edges[i][0];
                    int key2 = edges[i][1];

                    if (isEmpty){
                        if (!notCircle.containsKey(key1)){
                            result[0] = key1;
                            result[1] = key2;
                        }
                    }else {
                        if (key2 == son && circle.containsKey(key1)){
                            result[0] = key1;
                            result[1] = son;
                        }
                    }
                }
                return result;
            }
        }
        return result;
    }

    private int[] result2(int[][] edges,Map<Integer,Integer> m1,Map<Integer,List<Integer>> m2,Map<Integer,Integer> m3,Map<Integer,List<Integer>> m4){
        int[] result = new int[2];
        int root = 0;
        List<Integer> nodes = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry :m2.entrySet()) {
            root = entry.getKey();
            nodes = entry.getValue();
            nodes.add(m1.get(root));
            break;
        }

        Map<Integer,Boolean> circle = new HashMap<>(16);
        Map<Integer,Boolean> notCircle = new HashMap<>(16);
        for (Integer node:nodes) {
            if (cricle1(new int[]{root,node},root,m1)){
                circle.put(node,true);
            }else {
                notCircle.put(node,true);
            }
        }

        if (circle.size() == 1){
            for (int i=0;i<edges.length;i++) {
                // key1->key2
                int key1 = edges[i][0];
                int key2 = edges[i][1];
                if (!(key1 == root && notCircle.containsKey(key2))){
                    result[0] = key1;
                    result[1] = key2;
                }
            }
            return result;
        }else{
            for (int i=0;i<edges.length;i++) {
                // key1->key2
                int key1 = edges[i][0];
                int key2 = edges[i][1];
                if (key1 == root && circle.containsKey(key2)){
                    result[1] = key2;
                }
            }
            return result;
        }
    }

    private boolean cricle1(int[] dif,int end,Map<Integer,Integer> m){
        //2<-1->3
        //2->1<-3
        boolean flag = true;

        int son = dif[1];
        Integer next = son;
        int i = m.size();
        int hit = 0;
        while (i>=0){
            next = m.get(next);
            if ((next!=null && next.intValue() == end)){
                hit++;
                break;
            }
            i--;
        }

        if (hit == 0){
            flag = false;
        }

        return flag;
    }

}
