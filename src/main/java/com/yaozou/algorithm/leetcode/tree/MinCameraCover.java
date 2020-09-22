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
    public int minCameraCover(TreeNode root) {
        if (root == null){
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        int num = 0;
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            if (node.left != null){
                if (node.left.left != null || node.left.right != null){
                    if (node.left.left != null){
                        stack.push(node.left.left);
                    }
                    if (node.left.right != null){
                        stack.push(node.left.right);
                    }
                }
                num++;
            }
            if (node.right != null){
                if (node.right.left != null || node.right.right != null){
                    if (node.right.left != null){
                        stack.push(node.right.left);
                    }
                    if (node.right.right != null){
                        stack.push(node.right.right);
                    }
                }
                num++;
            }
        }

        if (num == 0){
            return 1;
        }

        return num;
    }

    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
