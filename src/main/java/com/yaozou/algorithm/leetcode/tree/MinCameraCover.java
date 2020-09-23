package com.yaozou.algorithm.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * created on 2020/9/22 18:27
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MinCameraCover {

    public static void main(String[] args) {
       /* print("[0,0,0]","[1,0,0]");
        print("[0,0,0,null,null,null,0]","[1,0,1,null,null,null,0]");
        print("[0,null,0,null,0,null,0]","[0,null,1,null,1,null,0]");
        print("[0]","[1]");
        print("[0,null,0,null,0,0,0]","[0,null,1,null,1,0,0]");

        print("[0,0,null,null,0,null,0]","[0,1,null,null,1,null,0]");
        print("[0,0,0,null,0,null,0]","[0,1,1,null,0,null,0]");

        print("[0,0,null,0,null,0,null,null,0]","[0,1,null,0,null,1,null,null,0]");*/
       print("[0,0,0,null,0,0,null,null,0]","[0,0,0,null,0,0,null,null,0]");
    }

    public static void print(String val,String result){
        MinCameraCover cameraCover = new MinCameraCover();
        TreeNode root1  = cameraCover.deserialize(val);
        cameraCover.minCameraCover(root1);
        String end = cameraCover.serialize(root1);
        System.out.println(result.equals(end)+ "  " + result + "  " + end);
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
        if (root == null){
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        int num = 0;
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            boolean flag = false;
            boolean left = node.left != null && (node.left.left != null || node.left.right != null);
            boolean right = node.right != null && (node.right.left != null || node.right.right != null);

            if (node.left == null && node.right == null){
                // 叶子节点
                flag = true;
            } else if (node.left != null && node.left.left == null && node.left.right == null){
                // 左节点为叶子节点
                flag = true;
            }else if (node.right != null && node.right.left == null && node.right.right == null){
                // 右节点为叶子节点
                flag = true;
            }else if (left && right && (deep(node.left) || deep(node.right))){
                // 左边/右边子树深度至少3层
                flag = true;
            }
            if (flag){
                node.val = 1;
                num ++;
                next(node,stack);
            }else{
                // 当前节点不适合安装监控，预测下级所有子节点是否合适并安装
                // 左边子树
                if (node.left != null){
                    num ++;
                    node.left.val = 1;
                    next(node.left,stack);
                }

                // 右子树
                if (node.right != null){
                    num ++;
                    node.right.val = 1;
                    next(node.right,stack);
                }
            }
        }

        return num;
    }

    private boolean deep(TreeNode node){
        boolean left = node.left != null && (node.left.left != null || node.left.right != null);
        boolean right = node.right != null && (node.right.left != null || node.right.right != null);

        return left || right;
    }

    private void next(TreeNode node,Stack<TreeNode> stack){
        // 预测下一个节点为监控
        if (node.left != null){
            if (nextNodeInstall(node.left)){
                stack.push(node.left);
            }else if (node.left.left != null){
                stack.push(node.left.left);
            }else if (node.left.right != null){
                stack.push(node.left.right);
            }
        }
        if (node.right != null){
            if (nextNodeInstall(node.right)){
                stack.push(node.right);
            }else if (node.right.left != null){
                stack.push(node.right.left);
            }else if (node.right.right != null){
                stack.push(node.right.right);
            }

        }

    }

    private boolean nextNodeInstall(TreeNode nextNode){
        boolean flag = false;

        if (nextNode.left != null && nextNode.left.left == null && nextNode.left.right == null){
            flag = true;
        }else if (nextNode.right != null && nextNode.right.left == null && nextNode.right.right == null){
            flag = true;
        }

        return flag;
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
