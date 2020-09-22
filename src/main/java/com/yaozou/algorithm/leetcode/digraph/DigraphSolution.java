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


        System.out.println("-----------------(1)即是子节点也是两个节点的父结点----------------");
        execute(new int[][]{{1,2}, {2,3},{3,4},{4,1},{1,5}},"4->1","{{1,2}, {2,3},{3,4},{4,1},{1,5}}");

        System.out.println("-----------------（2） 即是父结点也是两个节点的子节点----------------");
        execute(new int[][]{{1,2}, {1,3}, {2,3}},"2->3","{{1,2}, {1,3}, {2,3}}");
        execute(new int[][]{{4,2},{1,5},{5,2},{5,3},{1,4}},"5->2","{{4,2},{1,5},{5,2},{5,3},{1,4}}");
        execute(new int[][]{{2,1},{3,1},{4,2},{1,4}},"2->1","{{2,1},{3,1},{4,2},{1,4}}");
        execute(new int[][]{{1,5},{3,2},{2,4},{4,5},{5,3}},"4->5","{{1,5},{3,2},{2,4},{4,5},{5,3}}");

        System.out.println("-----------------（3）有两个节点异常，既有(1)也有(2)，并且（1）情况的节点是三个节点的父结点----------------");
        execute(new int[][]{{1,4},{5,2},{1,3},{4,5},{1,5}},"1->5","{{1,4},{5,2},{1,3},{4,5},{1,5}}");
        execute(new int[][]{{4,2},{1,5},{5,2},{4,3},{4,1}},"5->2","{{4,2},{1,5},{5,2},{4,3},{4,1}}");
        execute(new int[][]{{5,2},{1,5},{4,2},{4,3},{4,1}},"4->2","{{5,2},{1,5},{4,2},{4,3},{4,1}}");

        System.out.println("-----------------（4）有两个节点异常，既有(1)也有(2), 为（2）情况的节点它的一个子节点也是它的父结点----------------");
        execute(new int[][]{{4,2},{1,5},{5,2},{5,3},{2,4}},"4->2","{{4,2},{1,5},{5,2},{5,3},{2,4}}");
        execute(new int[][]{{3,5},{1,3},{2,1},{5,4},{2,3}},"2->3","{{3,5},{1,3},{2,1},{5,4},{2,3}}");

        System.out.println("-----------------(5) (4)和(3)----------------");
        execute(new int[][]{{4,1},{1,5},{4,2},{5,1},{4,3}},"5->1","{{4,1},{1,5},{4,2},{5,1},{4,3}}");

        execute(new int[][]{{9,3},{4,9},{3,1},{10,2},{5,4},{6,8},{6,5},{10,6},{2,3},{3,7}},"2->3","{{9,3},{4,9},{3,1},{10,2},{5,4},{6,8},{6,5},{10,6},{2,3},{3,7}}");

        System.out.println("-----------------other----------------");
       execute(new int[][]{{1,2}, {2,3}, {3,1}},"3->1","{{1,2}, {2,3}, {3,1}}");
    }

    private static void execute(int[][] edges,String str,String array){
        DigraphSolution solution = new DigraphSolution();
        int[] result = solution.findRedundantDirectedConnection(edges);
        String reStr = +result[0]+"->"+result[1];
        System.out.println(array+"  "+str+"  "+reStr+"->"+reStr.equals(str));
    }

    /**
     * 冗余连接 II
     * 在本问题中，有根树指满足以下条件的有向图。该树只有一个根节点，所有其他节点都是该根节点的后继。每一个节点只有一个父节点，除了根节点没有父节点。
     *
     * 输入一个有向图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
     *
     * 结果图是一个以边组成的二维数组。 每一个边 的元素是一对 [u, v]，用以表示有向图中连接顶点 u 和顶点 v 的边，其中 u 是 v 的一个父节点。
     *
     * 返回一条能删除的边，使得剩下的图是有N个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/redundant-connection-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param edges
     * @return
     */

    public int[] findRedundantDirectedConnection(int[][] edges) {
       // 仅存在一个子节点 父结点，子节点
       Map<Integer,List<Integer>> m1 = new HashMap<>(edges.length);
       // 两个子节点       父结点，子节点
       Map<Integer,Boolean> m2 = new HashMap<>(16);

       // 为一个父结点，一个子结点 子结点，父结点
        Map<Integer,List<Integer>> m3 = new HashMap<>(edges.length);
       // 为一个父结点，两个子结点  子结点，父结点
        Map<Integer,Boolean> m4 = new HashMap<>(16);

       for (int i=0;i<edges.length;i++){
           // key1->key2
           int key1 = edges[i][0];
           int key2 = edges[i][1];

           if (m1.containsKey(key1)){
               m2.put(key1,true);
            }

            if (m3.containsKey(key2)){
                m4.put(key2,true);
            }

           List<Integer> list = m1.getOrDefault(key1,new ArrayList<>());
           list.add(key2);
           m1.put(key1,list);

           List<Integer> list1 = m3.getOrDefault(key2,new ArrayList<>());
           list1.add(key1);
           m3.put(key2,list1);
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

    private int[] result1(int[][] edges,
                          Map<Integer,List<Integer>> m1,Map<Integer,Boolean> m2,
                          Map<Integer,List<Integer>> m3,Map<Integer,Boolean> m4){
        int[] result = new int[2];
        for (Map.Entry<Integer, Boolean> entry :m4.entrySet()) {

            int son = entry.getKey();
            List<Integer> list = m3.get(son);

            // 3->4 2->4 4->2
            if (m1.containsKey(son)){
                int v = m1.get(son).get(0);
                for (Integer node:list){
                    if (node == v){
                        result[1] = son;
                        result[0] = m1.get(son).get(0);
                        return result;
                    }
                }

            }

            Map<Integer,Boolean> circle = new HashMap<>(16);
            Map<Integer,Boolean> notCircle = new HashMap<>(16);
            for (Integer root:list) {
                // 4->2 start=4 end=2
                if (circle(root,son,m1,m3)){
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
                        if (notCircle.containsKey(key1)){
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

    private int[] result2(int[][] edges,
                          Map<Integer,List<Integer>> m1,Map<Integer,Boolean> m2,
                          Map<Integer,List<Integer>> m3,Map<Integer,Boolean> m4){
        int[] result = new int[2];
        for (Map.Entry<Integer, Boolean> entry :m2.entrySet()) {
            int root = entry.getKey();
            List<Integer> nodes = m1.get(root);

            Map<Integer,Boolean> circle = new HashMap<>(16);
            Map<Integer,Boolean> notCircle = new HashMap<>(16);
            for (Integer node:nodes) {
                // 4->2 start = 2 end=4
                if (circle(node,root,m1,m3)){
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
                result[0] = root;
                return result;
            }
        }
        return edges[edges.length-1];
    }

    // 4->2 start = 2 end=4
    private boolean circle(int start,int end,Map<Integer,List<Integer>> m1,Map<Integer,List<Integer>> m3){
        boolean flag = startJudge(start,end,end,m1,m3);
        return flag;
    }

    private boolean startJudge(int start,int end,int last,Map<Integer,List<Integer>> m1,Map<Integer,List<Integer>> m3){
        boolean flag = false;
        //
        if (m3.containsKey(start)){
            List<Integer> list = m3.get(start);
            for (Integer i : list) {
                if (i == last){
                    continue;
                }
                if (i == end){
                    return true;
                }
                flag = startJudge(i,end,start,m1,m3);
                if (flag){
                    return true;
                }
            }
        }

        if (!flag && m1.containsKey(start)){
            List<Integer> list = m1.get(start);
            for (Integer i : list) {
                if (i == last){
                    continue;
                }
                if (i==end){
                    return true;
                }
                flag = startJudge(i,end,start,m1,m3);
                if (flag){
                    return true;
                }
            }
        }

        return flag;
    }

}
