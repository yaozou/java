package com.yaozou.algorithm.leetcode.tree;

import java.util.Stack;

/**
 * created on 2020/9/22 18:27
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MinCameraCover {

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
    //[0,0,0]  [0,0,0,null,null,null,0] [0,null,0,null,0,null,0] [0] [0,null,0,null,0,0,0]
    public int minCameraCover(TreeNode root) {
        if (root == null){
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        int num = 0;
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            boolean flag = install(node);

            num ++;
            if (!flag){
                // 不适合安装监控时,将监控安装在下一个节点，由此从一个节点安装
                if (node.left != null && (node.left.left != null || node.left.right != null)){
                    node = node.left;
                }
                if (node.right != null && (node.right.left != null || node.right.right != null)){
                    node = node.right;
                }
            }

            // 预判下一个监控节点
            TreeNode nextLeft = node.left;
            next(nextLeft,stack);

            TreeNode nextRight = node.right;
            next(nextRight,stack);
        }

        return num;
    }

    private void next(TreeNode nextNode,Stack<TreeNode> stack){
        if (nextNode != null){
            // 预测下一个节点为监控
            if (install(nextNode)){
                stack.push(nextNode);
            }else{
                if (nextNode.left != null ){
                    stack.push(nextNode.left);
                }
                if (nextNode.right != null){
                    stack.push(nextNode.right);
                }
            }
        }

    }

    private boolean install(TreeNode node){
        boolean flag = false;
        boolean left = node.left != null && (node.left.left != null || node.left.right != null);
        boolean right = node.right != null && (node.right.left != null || node.right.right != null);

        if (node.left == null && node.right == null){
            flag = true;
        } else if (node.left != null && node.left.left == null && node.left.right == null){
            flag = true;
        }else if (node.right != null && node.right.left == null && node.right.right == null){
            flag = true;
        }else if (left && right){
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
}
