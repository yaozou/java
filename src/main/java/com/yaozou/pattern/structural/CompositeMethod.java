package com.yaozou.pattern.structural;

import lombok.Data;

import java.util.Enumeration;
import java.util.Vector;

/**
 *  组合模式
 *  部分-整体模式在处理类似树形结构的问题时比较方便
 */
public class CompositeMethod {
    public static void main(String[] args) {

    }
    @Data
    class TreeNode{
        private String name;
        private TreeNode parent;
        private Vector<TreeNode> children = new Vector<TreeNode>();

        public TreeNode(String name){
            this.name = name;
        }

        /*添加孩子节点*/
        public void add(TreeNode node){
            children.add(node);
        }

        /*删除孩子节点*/
        public void remove(TreeNode node){
            children.remove(node);
        }

        /*取得孩子节点*/
        public Enumeration<TreeNode> getChildren(){
            return children.elements();
        }
    }

    class Tree{
        TreeNode root = null;
        public Tree(String name){
            root = new TreeNode(name);
        }
    }

    public void run(){
        Tree tree = new Tree("A");
        TreeNode nodeB = new TreeNode("B");
        TreeNode nodeC = new TreeNode("C");

        nodeB.add(nodeC);
        tree.root.add(nodeB);
        System.out.println("build the tree finished!");
    }
}
