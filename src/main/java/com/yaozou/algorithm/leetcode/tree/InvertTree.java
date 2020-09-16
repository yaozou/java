package com.yaozou.algorithm.leetcode.tree;


import java.util.Stack;

/**
 * created on 2020/9/16 16:30
 *
 * @author yaozou
 * @since v1.0.0
 */
public class InvertTree {
    public static void main(String[] args) {
        int[] vals = new int[]{4,2,7,1,3,6,9};
        TreeNode root = new TreeNode(4);
        Stack<TreeNode> sNode = new Stack<>();
        sNode.add(root);
        int i = 1;
        while (!sNode.isEmpty()&& i < vals.length){
            TreeNode node = sNode.pop();
            node.left = new TreeNode(i);
            node.right = new TreeNode(++i);
            sNode.add(node.right);
            sNode.add(node.left);
        }

        invertTree(root);

        System.out.println();
    }
    public static TreeNode invertTree(TreeNode root) {
        if (root == null){return null;}
        Stack<TreeNode> sNode = new Stack<>();
        sNode.add(root);
        while (!sNode.isEmpty()){
           TreeNode node = sNode.pop();
           if (node.left != null){
               sNode.add(node.left);
           }
           if (node.right != null){
               sNode.add(node.right);
           }
           TreeNode left = node.left;
           node.left = node.right;
           node.right = left;

           System.out.println(node.val+"->"+node.left.val+"->"+node.right);
        }
        return root;
    }
}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}