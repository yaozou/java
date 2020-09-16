package com.yaozou.algorithm.leetcode.number;

import java.util.ArrayList;

/**
 * created on 2020/9/16 17:05
 *
 * @author yaozou
 * @since v1.0.0
 */
public class NumberSolution {
    public static void main(String[] args) {
        int[] num1 = new int[]{9};
        int[] num2 = new int[]{9,9,9,9,9,9,9,9,9,1};
        ListNode l1 = createNode(num1);
        ListNode l2 = createNode(num2);

        NumberSolution solution = new NumberSolution();
        ListNode sum = solution.addTwoNumbers1(l1,l2);
        System.out.println();
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int[] num1 = array(l1);
        int[] num2 = array(l2);
        boolean flag = num1.length > num2.length;
        int size = flag?num1.length:num2.length;
        if (flag){
           return result(num1,num2,size);
        }
        return result(num2,num1,size);
    }

    private ListNode result(int[] maxNum,int[] minNum,int size){
        int[] result = new int[size];
        int mod = 0;

    }

    public int[] array(ListNode ln){
        ListNode node = ln;
        int size = 0;
        while ((node = node.next) != null){
            size++;
        }
        node = ln;
        int i = 0;
        int[] nums = new int[size];
        while ((node = node.next) != null){
            nums[i] = node.val;
        }
        return nums;
    }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
                99999999, 999999999, Integer.MAX_VALUE };
        int sum = sum(l1)+sum(l2);
        int size = 0;
        for (int i=0;i<sizeTable.length ; i++){
            if (sum <= sizeTable[i]) {
                size = i + 1; break;
            }
        }
        ListNode root = new ListNode(sum%10);
        ListNode node = root;
        int p = sum / 10;
        for (int j = 1;j < size;j++){
            node.next = new ListNode(p%10);
            node = node.next;
            p = p / 10;
        }
        return root;
    }

    public int sum(ListNode ln){
        int sum = 0;
        ListNode node = ln;
        int i = 0;
        while (node != null){
            sum += node.val*(Math.pow(10D,i));
            node = node.next;
            i++;
        }
        return sum;
    }

    private void test(){
        int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
                99999999, 999999999, Integer.MAX_VALUE };
        int sum = 807;
        int size = 0;
        for (int i=0;i<sizeTable.length ; i++){
            if (sum <= sizeTable[i]) {
                size = i + 1; break;
            }
        }
        int p = sum;
        for (int j = 0;j < size;j++){
            System.out.print(p%10+"->");
            p = p / 10;
        }
        System.out.println();
    }

    private static ListNode createNode(int[] num){
        int length = num.length;
        ListNode root = new ListNode(num[length-1]);
        ListNode node = root;
        for (int i = length-2;i>=0;i--){
            node.next = new ListNode(num[i]);
            node = node.next;
        }
        return root;
    }
}
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
