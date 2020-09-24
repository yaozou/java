package com.yaozou.algorithm.leetcode.tree;

import java.util.*;

/**
 * created on 2020/9/22 18:27
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MinCameraCover {

    public static void main(String[] args) {
        print("[0,0,0]","1");
        print("[0,0,0,null,null,null,0]","2");
        print("[0,null,0,null,0,null,0]","2");
        print("[0]","1");
        print("[0,null,0,null,0,0,0]","2");

        print("[0,0,null,null,0,null,0]","2");
        print("[0,0,0,null,0,null,0]","2");

        print("[0,0,null,0,null,0,null,null,0]","2");
        print("[0,0,0,null,0,0,null,null,0]","2");

        print("[0,0,0,0,0,null,null,0,null,null,0,null,0,null,null,null,0]","4");
    }

    public static void print(String val,String result){
        MinCameraCover cameraCover = new MinCameraCover();
        TreeNode root1  = cameraCover.deserialize(val);
        int num = cameraCover.minCameraCover(root1);
        System.out.println(result.equals(num+"")+ " "+num+":" + result + " "+val);
    }

    /**
     * 给定一个二叉树，我们在树的节点上安装摄像头。
     *
     * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
     *
     * 计算监控树的所有节点所需的最小摄像头数量。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/binary-tree-cameras
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param root
     * @return
     */
    public int minCameraCover(TreeNode root) {
        if (root == null){return 0;}

        // 被监控的节点 key->节点 value->是否为摄像头
        Map<Integer,Boolean>  monitored = new HashMap<>(16);
        // 摄像头 key->节点 value->是否为一级节点
        Map<Integer,Boolean>  camera = new HashMap<>(16);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int level = 1;
        while (!queue.isEmpty()){
           int size = queue.size();
           boolean leaveNode = true;
           for (int i = 0;i<size;i++){
               TreeNode node = queue.poll();
               if (node == null){
                   queue.add(null);
                   queue.add(null);
                   continue;
               }
               if (node.left!=null && (node.left.left != null || node.left.right != null)){
                   leaveNode = false;
               }
               if (node.right!=null && (node.right.left != null || node.right.right != null)){
                   leaveNode = false;
               }
               queue.add(node.left);
               queue.add(node.right);
               int position = point(level,i);
               int parent = position/2;
              // 判断当前节点是否可安装监控
               boolean isMonitored = monitored.containsKey(position);
               if (!isMonitored || (isMonitored && levelEqOne(node))){
                    if (install(node) || (parent > 0 && !camera.containsKey(parent))){
                        camera.put(position,levelEqOne(node));
                        monitored.put(position,true);
                        if (node.left != null){
                            monitored.put(position<<1,false);
                        }
                        if (node.right != null){
                            monitored.put((position<<1)+1,false);
                        }
                        if (parent > 1 && !monitored.containsKey(parent)){
                            monitored.put(parent,false);
                        }
                        int mod = position % 2;
                        // 为右节点
                        if (mod > 0 && camera.containsKey(position-1) && !camera.get(position-1)){
                            int leftPosition = position-1;
                            camera.remove(leftPosition);
                            monitored.remove(leftPosition);
                            monitored.remove(leftPosition<<1);
                            monitored.remove((leftPosition<<1)+1);
                        }
                    }
               }
           }
            if (leaveNode){
                break;
            }
            level ++;

        }

        System.out.println(level +" "+camera.toString());
        return camera.size();
    }

    private boolean levelEqOne(TreeNode node){
        boolean left = false;
        boolean right = false;
        if (node.left != null && node.left.left == null && node.left.right == null){
            left = true;
        }
        if (node.right != null && node.right.left == null && node.right.right == null){
            right = true;
        }

        return left || right;
    }

    private boolean install(TreeNode node){
        boolean flag = false;

        if (node.left == null && node.right == null){
            // 叶子节点
            flag = true;
        } else if (node.left != null && node.left.left == null && node.left.right == null){
            // 左节点为叶子节点
            flag = true;
        }else if (node.right != null && node.right.left == null && node.right.right == null){
            // 右节点为叶子节点
            flag = true;
        }
        return flag;
    }

    private int point(int level,int num){
        return (int) (Math.pow(2,level-1)+num);
    }

    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    public TreeNode deserialize(String data) {
        if ((data == null || "".equals(data)) || (data.length() <=2)){return null;}
        data = data.substring(1,data.length()-1);
        String[] nodes = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = createTreeNode(nodes[0]);
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty() && i < nodes.length){
            TreeNode node = queue.poll();
            if (node == null){continue;}
            node.left = createTreeNode(nodes[i]);
            node.right = createTreeNode(nodes[i+1]);

            i += 2;
            queue.add(node.left);
            queue.add(node.right);
        }
        return root;
    }

    private TreeNode createTreeNode(String nodeStr){
        if ("null".equals(nodeStr)){return null;}
        return new TreeNode(Integer.valueOf(nodeStr));
    }

    public String serialize(TreeNode root) {
        if (root == null){return "[]";}
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        StringBuilder sb = new StringBuilder();
        sb.append(root.val);
        while (!queue.isEmpty()){
            int level = queue.size();
            boolean leaveNode = true;
            for (int i = 0;i<level;i++){
                TreeNode node = queue.poll();
                boolean isNull = true;
                String lVal = "null";
                if (node.left!=null){
                    lVal = node.left.val+"";
                    isNull = false;
                    queue.add(node.left);
                    if (node.left.left != null || node.left.right != null){leaveNode = false;}
                }

                String rVal = "null";
                if (node.right!=null){
                    rVal = node.right.val+"";
                    isNull = false;
                    queue.add(node.right);
                    if (node.right.left != null || node.right.right != null){leaveNode = false;}
                }
                if (!(isNull && queue.isEmpty())){
                    sb.append(",").append(lVal).append(",").append(rVal);
                }
            }
            if (leaveNode){
                break;
            }
        }
        return "["+sb.toString()+"]";
    }
}
