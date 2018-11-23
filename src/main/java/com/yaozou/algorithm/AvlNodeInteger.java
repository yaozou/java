package com.yaozou.algorithm;

import java.util.Map;
import java.util.TreeMap;

import lombok.Data;

/**
 * @Description:平衡二叉树
 * 地址：https://blog.csdn.net/zhang6622056/article/details/82698859
 * @author: yaozou
 * @Date: 2018/11/22 14:18
 */

@Data
public class AvlNodeInteger {
    private Integer value;
    private Integer height;
    private AvlNodeInteger left;
    private AvlNodeInteger right;

    public AvlNodeInteger(int t){
        initNode(t,null,null,1);
    }

    public AvlNodeInteger(int t,AvlNodeInteger left,AvlNodeInteger right){
        initNode(t,left,right,null);
    }

    private void initNode(int t,AvlNodeInteger left,
                          AvlNodeInteger right,Integer height){
        this.setValue(t);
        this.left = left;
        this.right = right;
        this.height = height;
    }
}

 class AvlUtils{
    private AvlNodeInteger root;
    private int size;
    private Map<Integer,Integer> vals = new TreeMap<>();

    public void insert(int val) throws Exception{
        if (root == null){
            initRoot(val);
        }else{
            if (contains(val)){
                throw new Exception("The value is already exist!");
            }
            insertNode(root,val);
            vals.put(val,size);
        }
        size ++;
    }

    public void remove(int val){
        if (root == null){
            return;
        }else if (!contains(val)){
            return;
        }
        removeNode(root,val);
    }

    private boolean contains(int val){
         return vals.containsKey(val);
    }

     private void initRoot(Integer val){
         AvlNodeInteger AvlNodeInteger = new AvlNodeInteger(val);
         this.root = AvlNodeInteger;
         System.out.println(this.root.getValue());
     }

    private AvlNodeInteger insertNode(AvlNodeInteger parent,Integer val){
        if (parent == null){
            return createSingleNode(val);
        }
        boolean leftFlag = true;
        if (parent.getValue() > val){
            // 小于根节点 （左节点）
            parent.setLeft(insertNode(parent,val));
        }else {
            // 大于根节点（右节点）
            parent.setRight(insertNode(parent,val));
            leftFlag = false;
        }

        // 回溯 (判定是否平衡)
        if (leftFlag){
            // 左
            if (parent.getLeft().getHeight()-parent.getRight().getHeight() > 1){
                int compareVal = parent.getLeft().getValue();
                if (val < compareVal){
                    parent = leftLeftRotate(parent);
                }else {
                    parent = leftRightRotate(parent);
                }
            }
        }else {
            //右
            if (parent.getRight().getHeight()-parent.getLeft().getHeight() > 1){
                int compareVal = parent.getLeft().getValue();
                if (val > compareVal){
                    parent = rightRightRotate(parent);
                }else {
                    parent = rightRightRotate(parent);
                }
            }
        }
        // 计算数的高度
        parent.setHeight(maxHeight(parent.getLeft(),parent.getRight()));
        return parent;
    }

    private AvlNodeInteger removeNode(AvlNodeInteger parent,int val){
        // 1、查找节点
        if (val < parent.getValue()){
            AvlNodeInteger newLeft = removeNode(parent.getLeft(),val);
            parent.setLeft(newLeft);
            // 3、检查平衡 删除的左边，那么用右边-左边
            if (height(parent.getRight())-height(parent.getLeft()) > 1){
                AvlNodeInteger tempNode = parent.getRight();
                if (height(tempNode.getLeft()) > height(tempNode.getRight())) {
                    rightLeftRotate(parent);
                }else{
                    rightRightRotate(parent);
                }
            }

        }else if (val > parent.getValue()){
            AvlNodeInteger newRight = removeNode(parent.getRight(),val);
            parent.setRight(newRight);
            // 3、检查平衡
            if (height(parent.getLeft())-height(parent.getRight()) > 1){
                AvlNodeInteger tempNode = parent.getRight();
                if (height(tempNode.getLeft()) > height(tempNode.getRight())) {
                    leftLeftRotate(parent);
                }else{
                    leftRightRotate(parent);
                }
            }
        }else{
            // 2、移除节点
            if (parent.getLeft() != null && parent.getRight() != null){
                removeNode(parent);
            }else{
                parent = null;
            }
        }
        return parent;
    }

    private AvlNodeInteger removeNode(AvlNodeInteger node){
        //判断高度，高的一方，拿到最大(左)，最小(右)的节点，作为替换节点。
        //删除原来匹配节点
        //左边更高，获取到左边最大的节点
        AvlNodeInteger newNode;
        int height;
         if (node.getLeft().getHeight() > node.getRight().getHeight()){
             newNode = getMaxNode(node);
             node.setLeft(removeNode(node.getLeft(),newNode.getValue()));
             height = maxHeight(newNode.getLeft(),newNode.getRight());
         }else{
             newNode = getMinNode(node);
             node.setRight(removeNode(node.getRight(),newNode.getValue()));
             height = maxHeight(newNode.getLeft(),newNode.getRight())+1;
         }
        newNode.setLeft(node.getLeft());
        newNode.setRight(node.getRight());
        newNode.setHeight(height);
        return newNode;
    }

    private AvlNodeInteger getMaxNode(AvlNodeInteger node){
        if (node.getRight() != null){
            node = getMaxNode(node.getRight());
        }
        return node;
    }

     private AvlNodeInteger getMinNode(AvlNodeInteger node){
        if (node.getLeft() != null){
            node = getMinNode(node.getLeft());
        }
        return node;
     }

    private AvlNodeInteger createSingleNode(Integer val){
        return new AvlNodeInteger(val);
    }


     private int height(AvlNodeInteger t){
         return t==null?0:t.getHeight();
     }

     /***
      * 求左右子节点最大高度
      * @param left
      * @param right
      * @return
      */
     private int maxHeight(AvlNodeInteger left,AvlNodeInteger right){
         return height(left) > height(right) ? height(left)  : height(right);
     }

     /**
      * 左左旋
      * @param node 旋转之前的parent node 节点
      * @return 旋转之后的parent node节点
      */
     private AvlNodeInteger leftLeftRotate(AvlNodeInteger node){
         AvlNodeInteger newNode = node.getLeft();
         node.setLeft(newNode.getRight());
         newNode.setRight(node);

         //重新计算高度
         //node的高度降低，newNode的高度提高
         //newNode的高度由node高度确定
         node.setHeight(maxHeight(node.getLeft(),node.getRight()));
         newNode.setHeight(maxHeight(newNode.getLeft(),newNode.getRight()));
         return newNode;
     }

     private AvlNodeInteger rightRightRotate(AvlNodeInteger node){
         AvlNodeInteger newNode = node.getRight();
         node.setRight(newNode.getLeft());
         newNode.setLeft(node);

         node.setHeight(maxHeight(node.getLeft(),node.getRight()));
         newNode.setHeight(maxHeight(newNode.getLeft(),newNode.getRight()));
         return newNode;
     }

     /**
      * 左右旋 先右右旋再左左旋
      * @param node
      * @return
      */
     private AvlNodeInteger leftRightRotate(AvlNodeInteger node){
         node.setLeft(rightRightRotate(node.getLeft()));
         return leftLeftRotate(node);
     }

     /**
      * 右左旋 先右右旋再左左旋
      * @param node
      * @return
      */
     private AvlNodeInteger rightLeftRotate(AvlNodeInteger node){
         node.setRight(leftLeftRotate(node.getRight()));
         return rightRightRotate(node);
     }
}
