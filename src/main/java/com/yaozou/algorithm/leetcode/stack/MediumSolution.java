package com.yaozou.algorithm.leetcode.stack;

import java.util.*;

/**
 * created on 2021/1/15 14:54
 *
 * @author yaozou
 * @since v1.0.0
 */
public class MediumSolution {
    /** 移除最多的同行或同列石头 */
    public int removeStones(int[][] stones) {
        UnionFind unionFind = new UnionFind();
        for (int[] stone:stones){
            unionFind.union(stone[0] + 10001, stone[1]);
        }
        return stones.length-unionFind.count;
    }
    private class UnionFind{
        // key为行时，value为列
        // key为列时，value为列
        private Map<Integer,Integer> parent;
        private int count;
        public UnionFind() {
            this.parent = new HashMap<>();
            this.count = 0;
        }
        public int find(int x){
            if (!parent.containsKey(x)){
                parent.put(x,x);
                count++;
            }
            if (x != parent.get(x)){
                parent.put(x,find(parent.get(x)));
            }
            return parent.get(x);
        }

        public void union(int x,int y){
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY){return;}
            parent.put(rootX,rootY);
            count--;
        }
    }

    /** 二叉树的锯齿形层序遍历 */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        //即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行
        List<List<Integer>> all = new ArrayList<>();
        if (root == null){return all;}
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        boolean left = true;
        while (!stack.isEmpty()){
            int level = stack.size();
            List<Integer> list = new ArrayList<>();
            Stack<TreeNode> cached = new Stack<>();
            for (int i = 0;i<level;i++){
                TreeNode node = stack.pop();
                list.add(node.val);
                if (left){
                    if (node.left != null){
                        cached.add(node.left);
                    }
                    if (node.right != null){
                        cached.add(node.right);
                    }
                }else{
                    if (node.right != null){
                        cached.add(node.right);
                    }
                    if (node.left != null){
                        cached.add(node.left);
                    }
                }
            }
            stack.addAll(cached);
            left = !left;
            all.add(list);
        }
        return all;
    }

    /** 逆波兰表达式求值 */
    public int evalRPN(String[] tokens) {
        /**
         * 逆波兰表达式是一种后缀表达式，所谓后缀就是指算符写在后面。
         *     平常使用的算式则是一种中缀表达式，如 ( 1 + 2 ) * ( 3 + 4 ) 。
         *     该算式的逆波兰表达式写法为 ( ( 1 2 + ) ( 3 4 + ) * ) 。
         *
         * 逆波兰表达式主要有以下两个优点：
         *     去掉括号后表达式无歧义，上式即便写成 1 2 + 3 4 + * 也可以依据次序计算出正确结果。
         *     适合用栈操作运算：遇到数字则入栈；遇到算符则取出栈顶两个数字进行计算，并将结果压入栈中。
         *
         */
        Stack<Integer> stack = new Stack<>();
        String sub = "-",add = "+",divide = "/",mul="*";
        for (String token:tokens) {
            if (sub.equals(token)){
                int n1 = stack.pop();
                int n2 = stack.pop();
                stack.add(n2-n1);
            }else if (add.equals(token)){
                stack.add(stack.pop()+stack.pop());
            }else if (divide.equals(token)){
                int n1 = stack.pop();
                int n2 = stack.pop();
                stack.add(n2/n1);
            }else if (mul.equals(token)){
                stack.add(stack.pop()*stack.pop());
            }else{
                stack.add(new Integer(token));
            }
        }
        return stack.peek();
    }

    class BSTIterator {
        Queue<Integer> queue = new LinkedList<>();
        public BSTIterator(TreeNode root) {
           // 中序遍历二叉树 左根右
            Stack<TreeNode> stack = new Stack<>();
            TreeNode curr = root;
            while (curr != null || !stack.isEmpty()){
                if (curr != null){
                    stack.push(curr);
                    curr = curr.left;
                }else{
                    curr = stack.pop();
                    queue.add(curr.val);
                    curr = curr.right;
                }
            }

        }

        public int next() {
            return queue.poll();
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }
    }

    public int calculate(String s) {
        Deque<String> all = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (char c:s.toCharArray()) {
            if (' ' == c){continue;}
            else if (c >= '0' && c <= '9'){
                sb.append(c);
            }else{
                all.add(sb.toString());
                all.add(new String(new char[]{c}));
                sb.delete(0,sb.length());
            }
        }
        all.add(sb.toString());

        String divide = "/",mul="*",sub = "-",add = "+";
        Deque<Integer> nums = new ArrayDeque<>();
        Deque<String> mathematicalOperator = new ArrayDeque<>();
        while (!all.isEmpty()) {
            String c = all.pop();
            if (c .equals(divide) ){
                nums.add(nums.pollLast()/ new Integer(all.pop()));
            }else if (mul.equals(c)){
                nums.add(nums.pollLast()* new Integer(all.pop()));
            }else if (sub.equals(c) || add.equals(c)){
                mathematicalOperator.add(c);
            }else{
                nums.add(new Integer(c));
            }
        }

        int val = nums.pop();
        while (!mathematicalOperator.isEmpty()){
            String c = mathematicalOperator.pop();
            if (add.equals(c)){
                val = val + nums.pop();
            }else {
                val = val - nums.pop();
            }
        }
        return val;
    }

    public String removeDuplicateLetters(String s) {
        char[] chars = s.toCharArray();
        boolean[] vis = new boolean[26];
        int[] num = new int[26];
        for (char c : chars){
            num[c-'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (char c:chars) {
            if (!vis[c-'a']){
                while (sb.length() > 0 && sb.charAt(sb.length()-1)>c){
                    if (num[sb.charAt(sb.length()-1)-'a']>0){
                        vis[sb.charAt(sb.length()-1)-'a'] = false;
                        sb.deleteCharAt(sb.length()-1);
                    }else {
                        break;
                    }
                }
                vis[c-'a'] = true;
                sb.append(c);
            }
            num[c - 'a'] -= 1;
        }

        return sb.toString();
    }

    private class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int x){val = x;}
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        MediumSolution solution = new MediumSolution();
        // {1,0},{0,1},{1,1}
        // {0,0},{0,1},{1,0},{1,2},{2,1},{2,2}
//        System.out.println(solution.removeStones(new int[][]{{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}}));
        solution.calculate(" 3+5 / 2 ");
    }
}
