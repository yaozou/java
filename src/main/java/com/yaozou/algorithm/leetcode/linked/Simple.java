package com.yaozou.algorithm.leetcode.linked;


import java.util.Stack;

/**
 * created on 2020/10/19 18:21
 *
 * @author yaozou
 * @since v1.0.0
 */
public class Simple {
    /**
     * 21. 合并两个有序链表
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        Stack<ListNode> stack1 = new Stack<>();
        if (l1 != null){
            stack1.add(l1);
        }
        Stack<ListNode> stack2 = new Stack<>();
        if (l2!=null){
            stack2.add(l2);
        }

        ListNode root = null;
        ListNode next = null;

        while (stack1.isEmpty()){
            ListNode node1 = stack1.peek();
            ListNode node2 = stack2.peek();
            int val = node1.val;int val2 = 0;
            boolean flag = false;
            if (node2 != null){
                val2 = node2.val;
                flag = node1.val >= node2.val;
            }
            if (flag){
                stack2.add(node2.next);
                val = node2.val;val2 = node1.val;
            }else{
                stack2.add(node2);
            }
            if (root == null){
                root = new ListNode(val);
                if (flag){
                    root.next = new ListNode(val2);
                    next = root.next;
                }else{
                    next = root;
                }
            }else{
                next.next = new ListNode(val);
                next = next.next;
                if (flag){
                    next.next = new ListNode(val2);
                    next = next.next;
                }
            }
            stack1.add(node1.next);
        }

        while (stack2.isEmpty()){
            ListNode node = stack2.peek();
            if (root == null){
                root = new ListNode(node.val);
                next = root;
            }else{
                next.next = new ListNode(node.val);
                next = next.next;
            }
        }

        return root;
    }

    private class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    private ListNode create(int[] vals){
        ListNode root = null;
        ListNode next = null;
        for (int i = 0;i<vals.length;i++){
            int val = vals[i];
            if (root == null){
                root = new ListNode(val);
                next = root;
            }else{
                next.next = new ListNode(val);
                next = next.next;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        Simple s = new Simple();
        ListNode l1 = s.create(new int[]{});
        ListNode l2 = s.create(new int[]{});
    }
}
