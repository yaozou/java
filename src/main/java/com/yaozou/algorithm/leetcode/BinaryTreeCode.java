package com.yaozou.algorithm.leetcode;

import java.util.*;

/**
 * created on 2020/7/3 16:02
 * 二叉树
 * @author yaozou
 * @since v1.0.0
 */
public class BinaryTreeCode {

    public static void main(String[] args) {
        /*TreeNode left = new TreeNode(4);
        left.left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode root = new TreeNode(1);
        root.left = left;
        root.right = right;



        BinaryTreeCode binaryTreeCode = new BinaryTreeCode();
        *//*binaryTreeCode.inorderTraversal(root);*//*
        binaryTreeCode.hasPathSum2(root,10);*/

        /*int[] preorder = new int[]{3,9,20,15,7};
        int[] inorder = new int[]{9,3,15,20,7};
        buildTreePreAndIn(preorder,inorder);*/

        // [1,2,3,null,null,4,5]
        // [1,null,2,null,3,null,4,null,5,null,6,null,7]
        String data = "[1,null,2,null,3,null,4,null,5,null,6,null,7]";
        BinaryTreeCode tree = new BinaryTreeCode();
        String result = tree.serialize(tree.deserialize(data));
        System.out.println(result.equals(data));
        System.out.println(data);
        System.out.println(result);
}

    // Encodes a tree to a single string.
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

    // Decodes your encoded data to tree.
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

    private TreeNode ans;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        this.dfs(root, p, q);
        return this.ans;
    }

    private boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {return false;}
        boolean lson = dfs(root.left, p, q);
        boolean rson = dfs(root.right, p, q);
        if ((lson && rson) || ((root.val == p.val || root.val == q.val) && (lson || rson))) {
            ans = root;
        }
        return lson || rson || (root.val == p.val || root.val == q.val);
    }

    public Node connect(Node root) {
        if (root == null){return null;}

        Queue<Node> stack = new LinkedList<>();
        stack.add(root);

        while (!stack.isEmpty()){
            int level = stack.size();
            Queue<Node> nodes = new LinkedList<>();
            System.out.println(level);
            for (int i = 0;i<level;i++){
                Node node = stack.poll();
                System.out.print("val:"+node.val);
                Node last = null;
                if (!nodes.isEmpty()){last=nodes.poll();}
                if (last != null){
                    last.next = node;
                    System.out.print(" last val:"+last.val);
                }
                nodes.add(node);

                Node left = node.left;
                Node right = node.right;
                if (left != null){
                    stack.add(left);
                }
                if (right != null){
                    stack.add(right);
                }
                System.out.println();
            }

            System.out.println("----------------");
        }
        return root;
    }

    public static TreeNode buildTreePreAndIn(int[] preorder, int[] inorder) {
        if (inorder==null || preorder==null || inorder.length != preorder.length){return null;}
        // preorder root -> left -> right
        // inorder  left -> root -> right
        return helpBuildTreePreAndIn(preorder,inorder);
    }

    private static TreeNode helpBuildTreePreAndIn(int[] preorder, int[] inorder) {
        if (inorder.length == 0 || preorder.length == 0 ){
            return null;
        }
        TreeNode rootNode = new TreeNode(preorder[0]);
        for (int i=0;i<inorder.length;i++){
            if (inorder[i] == preorder[0]){
                int[] preLeft = Arrays.copyOfRange(preorder,1,i+1);
                int[] preRight = Arrays.copyOfRange(preorder,i+1,preorder.length);

                int[] inLeft = Arrays.copyOfRange(inorder,0,i);
                int[] inRight = Arrays.copyOfRange(inorder,i+1,inorder.length);

                rootNode.left = helpBuildTreePreAndIn(preLeft,inLeft);
                rootNode.right = helpBuildTreePreAndIn(preRight,inRight);
            }
        }
        return rootNode;
    }

    public TreeNode buildTreeByInAndPost(int[] inorder, int[] postorder) {
        if (inorder==null || postorder==null || inorder.length != postorder.length){return null;}
        // postorder:  left -> right -> root
        // inorder:    left -> root -> right
        return helpBuildTree(inorder,postorder);
    }

    public TreeNode helpBuildTree(int[] inorder, int[] postorder) {
        if (inorder.length == 0 || postorder.length == 0 ){
            return null;
        }
        // 1.get root node. postorder:last node is root node
        TreeNode rootNode = new TreeNode(postorder[postorder.length-1]);
        // 2. loop left tree
        for (int i=0;i<inorder.length;i++){
            if (inorder[i] == postorder[postorder.length-1]){
                // get left and right tree
                int[] inleft = Arrays.copyOfRange(inorder,0,i);
                int[] inright = Arrays.copyOfRange(inorder,i+1,inorder.length);

                int[] postLeft = Arrays.copyOfRange(postorder,0,i);
                int[] postRight = Arrays.copyOfRange(postorder,i,postorder.length-1);

                rootNode.left = helpBuildTree(inleft,postLeft);
                rootNode.right = helpBuildTree(inright,postRight);
                break;
            }
        }
        return rootNode;
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null){return false;}
        if(root.left == null && root.right == null){return sum - root.val == 0;}

        return hasPathSum(root.left,sum-root.val) || hasPathSum(root.right,sum-root.val);
    }

    public boolean hasPathSum2(TreeNode root, int sum) {
        if (root == null){return false;}
        if(root.left == null && root.right == null){return sum - root.val == 0;}

        Stack<TreeNode> sNode = new Stack<>();
        Stack<Integer>  sVal = new Stack<>();
        sNode.add(root);
        sVal.add(sum-root.val);

        while (!sNode.isEmpty()){
            TreeNode node = sNode.pop();
            int      val  = sVal.pop();
            System.out.println(node.val+" "+val);

            if (node.left == null && node.right == null){
                if (val == 0){return true;}
                continue;
            }

            if (node.left != null){
                sNode.add(node.left);
                sVal.add(val-node.left.val);
            }

            if (node.right != null){
                sNode.add(node.right);
                sVal.add(val-node.right.val);
            }
        }

        return false;
    }

    public boolean isSymmetric1(TreeNode root) {
        return isMirror(root,root);
    }
    private boolean isMirror(TreeNode node1,TreeNode node2){
        if (node1 == null && node2 == null){return true;}
        if (node1 == null || node2 == null){return false;}
        return (node1.val == node2.val) && (isMirror(node1.right,node2.left)) && (isMirror(node1.left,node2.right));
    }

    public boolean isSymmetric2(TreeNode root) {
       if (root == null){return true;}
       Queue<TreeNode> queue = new LinkedList<>();
       queue.add(root.left);
       queue.add(root.right);

       while(!queue.isEmpty()){
           TreeNode node1 = queue.poll();
           TreeNode node2 = queue.poll();
           if (node1 == null && node2 == null){continue;}
           if (node1 == null || node2 == null || node1.val != node2.val){return false;}
           queue.add(node1.left);
           queue.add(node2.right);

           queue.add(node1.right);
           queue.add(node2.left);
       }

       return true;
    }

    public int maxDepth1(TreeNode root) {
        if (root == null){return 0;}
        Queue<TreeNode> stack = new LinkedList<>();
        stack.add(root);
        int num = 0;
        while (!stack.isEmpty()){
            int level = stack.size();
            List<Integer> vals = new ArrayList<>();
            for (int i = 1;i<=level;i++){
                TreeNode node = stack.poll();
                vals.add(node.val);
                if (node.left != null){stack.add(node.left);}
                if (node.right != null){stack.add(node.right);}
            }
            num ++ ;
        }
        return num;
    }

    public int maxDepth2(TreeNode root) {
        if (root == null){return 0;}
        return Math.max(maxDepth2(root.left),maxDepth2(root.right))+1;
    }

    public int maxDepth3(TreeNode root) {
        if (root == null){return 0;}
        int preCount = 1;int pCount = 0;int level = 0;
        Queue<TreeNode> stack = new LinkedList<>();
        stack.add(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.poll();
            preCount--;

            if (node.left != null){
                stack.add(node.left);
                pCount ++;
            }

            if (node.right != null){
                stack.add(node.right);
                pCount ++;
            }

            if (preCount == 0){
                preCount = pCount;
                pCount = 0;
                level++;
            }
        }

        return level;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null){return list;}

        Queue<TreeNode> stack = new LinkedList<>();
        stack.add(root);

        while (!stack.isEmpty()){
            int level = stack.size();
            List<Integer> vals = new ArrayList<>();
            for (int i = 1;i<=level;i++){
                TreeNode node = stack.poll();
                vals.add(node.val);
                if (node.left != null){stack.add(node.left);}
                if (node.right != null){stack.add(node.right);}
            }

            list.add(vals);
        }

        return list;
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        /*return preMethod1(root);
        return preMethod2(root);
        return preMethod3(root);*/
        return preMethod4(root);
    }

    public List<Integer> inorderTraversal(TreeNode root){
        /*return inMethod1(root);*/
        return inMethod2(root);
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        /*return postMethod1(root);*/
        return postMethod2(root);
    }

    public List<Integer> postMethod1(TreeNode root){
        // left - right - root
        List<Integer> list = new ArrayList<>();
        if(root != null){
            if(root.left != null){
                list.addAll(postorderTraversal(root.left));
            }
            if(root.right != null){
                list.addAll(postorderTraversal(root.right));
            }
            list.add(root.val);
        }
        return list;
    }

    public List<Integer> postMethod2(TreeNode root){
        // left - right - root
        List<Integer> list = new ArrayList<>();
        if(root == null){return list;}

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode curr = stack.pop();
            list.add(0,curr.val);
            if (curr.left != null){
                stack.push(curr.left);
            }
            if (curr.right != null){
                stack.push(curr.right);
            }
        }

        return list;
    }

    public List<Integer> inMethod1(TreeNode root){
        // left - root - right
        List<Integer> list = new ArrayList<>();
        if(root != null){
            if(root.left != null){
                list.addAll(inorderTraversal(root.left));
            }
            list.add(root.val);
            if(root.right != null){
                list.addAll(inorderTraversal(root.right));
            }
        }
        return list;
    }

    public List<Integer> inMethod2(TreeNode root){
        // left - root - right
        List<Integer> list = new ArrayList<>();
        if(root == null){return list;}

        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()){
            if (curr != null){
                stack.push(curr);
                curr = curr.left;
            }else{
                curr = stack.pop();
                list.add(curr.val);
                curr = curr.left;
            }
        }
        return list;
    }

    public List<Integer> preMethod1(TreeNode root){
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

    public List<Integer> preMethod2(TreeNode root){
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

    public List<Integer> preMethod3(TreeNode root){
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

    public List<Integer> preMethod4(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if(root == null){return list;}

        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        deque.add(root);
        // 根左右
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

  static class Node{
      public int val;
      public Node left;
      public Node right;
      public Node next;

      public Node() {}

      public Node(int _val) {
          val = _val;
      }

      public Node(int _val, Node _left, Node _right, Node _next) {
          val = _val;
          left = _left;
          right = _right;
          next = _next;
      }
  }
}
