package com.yaozou.algorithm.leetcode.number;

/**
 * created on 2020/9/16 17:05
 *
 * @author yaozou
 * @since v1.0.0
 */
public class NumberSolution {
    public static void main(String[] args) {
        // [9]
        //[1,9,9,9,9,9,9,9,9,9]
        int[] num1 = new int[]{9};
        int[] num2 = new int[]{1,9,9,9,9,9,9,9,9,9};
        ListNode l1 = createNode(num1);
        ListNode l2 = createNode(num2);

        NumberSolution solution = new NumberSolution();
        ListNode sum = solution.addTwoNumbers(l1,l2);
        System.out.println();
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode node1 = l1;
        ListNode node2 = l2;

        ListNode root = null;
        ListNode node = null;
        int mod = 0;
       while (node1 != null){
            int sum = node1.val;
            if (node2 != null){
                sum +=node2.val;
                node2 = node2.next;
            }
            sum += mod;
            mod = sum>=10?1:0;
            int val=sum>=10?sum-10:sum;
            if (root == null && node == null){
                root = node = new ListNode(val);
            }else{
                node.next = new ListNode(val);
                node = node.next;
            }

           node1 = node1.next;
            if (node1 == null && node2!=null){
                node1 = node2;
                node2 = null;
            }
        }
        if (mod > 0){
            node.next = new ListNode(mod);
        }
        return root;
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        int[] num1 = array(l1);
        int[] num2 = array(l2);
        boolean flag = num1.length > num2.length;
        if (flag){
           return result(num1,num2);
        }
        return result(num2,num1);
    }

    private ListNode result(int[] maxNum,int[] minNum){
        ListNode root = null;
        ListNode node = null;
        int mod = 0;
        for (int i = 0;i<maxNum.length;i++){
            int sum = maxNum[i];
            if (i< minNum.length){
                sum += minNum[i];
            }
            sum += mod;
            mod = sum>=10?1:0;
            int val=sum>=10?sum-10:sum;
            if (root == null && node == null){
                root = node = new ListNode(val);
            }else{
                node.next = new ListNode(val);
                node = node.next;
            }
        }
        if (mod > 0){
            node.next = new ListNode(mod);
        }

        return root;
    }

    public int[] array(ListNode ln){
        ListNode node = ln;
        int size = 0;
        while (node != null){
            size++;
            node = node.next;
        }

        node = ln;
        int i = 0;
        int[] nums = new int[size];
        while (node != null){
            nums[i] = node.val;
            i++;
            node = node.next;
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
        ListNode root = new ListNode(num[0]);
        ListNode node = root;
        for (int i = 1;i<=length-1;i++){
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
