package com.yaozou.algorithm.LeetCode;

import java.util.*;

/**
 * created on 2020/7/3 16:02
 *
 * @author yaozou
 * @since v1.0.0
 */
public class BinaryTreeCode {

    public static void main(String[] args) {
        TreeNode left = new TreeNode(4);
        left.left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode root = new TreeNode(1);
        root.left = left;
        root.right = right;



        BinaryTreeCode binaryTreeCode = new BinaryTreeCode();
        binaryTreeCode.preorderTraversal(root);
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        return method4(root);
    }

    public List<Integer> method1(TreeNode root){
        List<Integer> list = new ArrayList<>();
        // root - left - right
        if(root != null){
            list.add(root.val);
            if(root.left != null){
                list.add(root.left.val);
                if(root.left.left != null){
                    list.addAll(preorderTraversal(root.left.left));
                }
                if( root.left.right != null){
                    list.addAll(preorderTraversal(root.left.right));
                }
            }
            if(root.right != null){
                list.add(root.right.val);
                if(root.right.left != null){
                    list.addAll(preorderTraversal(root.right.left));
                }
                if(root.right.right != null){
                    list.addAll(preorderTraversal(root.right.right));
                }
            }
        }

        return list;
    }

    public List<Integer> method2(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if(root == null){return list;}

        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            list.add(node.val);

            if(node.right != null){
                stack.push(node.right);
            }

            if(node.left != null){
                stack.push(node.left);
            }
        }
        return list;
    }

    public List<Integer> method3(TreeNode root){
        List<Integer> list = new LinkedList<>();
        if(root == null){return list;}

        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.add(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pollLast();
            list.add(node.val);
            if(node.right != null){
                stack.add(node.right);
            }
            if(node.left != null){
                stack.add(node.left);
            }
        }

        return list;
    }

    public List<Integer> method4(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if(root == null){return list;}

        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        deque.add(root);
        while(!deque.isEmpty()){
            TreeNode node = deque.pollLast();
            list.add(node.val);
            if(node.right != null){
                deque.add(node.right);
            }
            if(node.left != null){
                deque.add(node.left);
            }
        }
        return list;
    }

    static class TreeNode {
        int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
}
