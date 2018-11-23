package com.yaozou.algorithm;

import lombok.Data;

/**
 * @Description:红黑树
 * 地址：https://blog.csdn.net/zhang6622056/article/details/82765690
 * @author: yaozou
 * @Date: 2018/11/23 10:43
 */
@Data
public class RbNodeInteger {
   private boolean red;
   private int val;
   private RbNodeInteger parent;
   private RbNodeInteger left;
   private RbNodeInteger right;

   public RbNodeInteger(int val){
       initNode(parent,null,null,val,true);
   }
   public RbNodeInteger(RbNodeInteger parent,RbNodeInteger left,
                         RbNodeInteger right,int val,boolean red){
       initNode(parent,left,right,val,red);
   }

   private void initNode(RbNodeInteger parent,RbNodeInteger left,
                         RbNodeInteger right,int val,boolean red){
       this.red = red;
       this.val = val;
       this.parent = parent;
       this.left = left;
       this.right = right;
   }
    /** 获取父节点 */
    public RbNodeInteger getFather(){
        return this.parent;
    }
    /** 获得父节点的兄弟节点 */
    public RbNodeInteger getUncle() {
        if (parent == null){
            return null;
        }
        if (parent.getFather() == null){
            return null;
        }
        if (parent == parent.getFather().getLeft()){
            return parent.getFather().getRight();
        }else{
            return parent.getFather().getLeft();
        }
    }

    /** 获得祖父节点 */
    public RbNodeInteger getGrandFather(){
        if (parent == null){
            return null;
        }
        return parent.parent;
    }

    /** 获得兄弟节点 */
    public RbNodeInteger getBrother(){
        if (parent == null){
            return null;
        }
        if (parent == parent.getLeft()){
            return parent.getRight();
        }else{
            return parent.getLeft();
        }
    }

}

@Data
class RbTreeUtils{
    /** 根节点 */
    private RbNodeInteger root;

    public void insert(int val){
        if (root == null){
            root = new RbNodeInteger(val);
            setBlack(root);
        }
        RbNodeInteger node = insertNode(val);
        rebuildAfterInsert(node);
    }

    public void remove(int val) throws Exception{
        RbNodeInteger removeNode;
        if ((removeNode = findValue(root,val)) == null){
            throw new Exception("要删除的节点不存在!");
        }
        removeNode(removeNode);
    }

    private RbNodeInteger insertNode(int val){
        RbNodeInteger node = new RbNodeInteger(val);
        RbNodeInteger father = root;
        RbNodeInteger tempFather = null;
        while (father != null){
            tempFather = father;
            if (val < father.getVal()){
                father = father.getLeft();
            }else {
                father = father.getRight();
            }
        }
        if (val < tempFather.getVal()){
            tempFather.setLeft(node);
        }else{
            tempFather.setRight(node);
        }
        node.setParent(tempFather);
        return node;
    }

    /**
     * 重新构建红黑树
     * @param node
     */
    private void rebuildAfterInsert(RbNodeInteger node){
        while (null != node.getFather() && node.getFather().isRed() && node.isRed()){
            if (null != node.getFather() && node.getFather().isRed()
                    && node.getUncle() != null && node.getUncle().isRed()){
                // 红黑黑 变换后父节点黑、父节点的兄弟节点黑、祖父节点红，自身红
                redBlackBlack(node.getGrandFather());
                node = node.getGrandFather();
            }else{
                // 黑红红 先旋转 再着色
                if (null != node.getGrandFather() &&
                        node.getGrandFather().getLeft() == node.getFather()){
                    if (node == node.getFather().getLeft()){
                        node = rightRotate(node.getFather());
                    }else {
                        node = leftRotate(node);
                        node = rightRotate(node);
                    }
                }else {
                    if (null != node.getGrandFather() &&
                            node.getGrandFather().getRight() == node.getFather()){
                        node = rightRotate(node);
                        node = leftRotate(node);
                    }else{
                        node = leftRotate(node.getFather());
                    }
                }
                blackRedRed(node);
            }
        }
        setBlack(root);
    }

    private void removeNode(RbNodeInteger node){
        if (null != node.getLeft() && null != node.getRight()){
            // 左边最大方案.找到替代节点，替换！转换思维删除单节点
            RbNodeInteger replaceNode = node.getLeft();
            while (replaceNode.getRight() != null){
                replaceNode = replaceNode.getRight();
            }
            boolean color = replaceNode.isRed();

            //判断node节点是否为root节点
            if (null == node.getFather()){
                this.root = replaceNode;
            }else{
                if (node.getFather().getLeft() == node){
                    node.getFather().setLeft(replaceNode);
                }else{
                    node.getFather().setRight(replaceNode);
                }
            }
            replaceNode.setRed(node.isRed());

            //如果替换节点是直接子节点，那么则不用再指定相应方向的子节点了，否则无限关联
            if (node.getLeft() != node){
                replaceNode.setLeft(replaceNode.getLeft());
                replaceNode.getLeft().setParent(replaceNode);
            }
            if (node.getRight() != node){
                replaceNode.setRight(replaceNode.getRight());
                replaceNode.getRight().setParent(replaceNode);
            }
            replaceNode.setParent(node.getParent());

            //如果替换节点的颜色是黑色，那么替换节点原来所在的地方应该已经丢失了一个黑色节点，
            //此时就需要重新调整数目
            if (!color){
                removeRebuildRb(replaceNode,replaceNode.getParent());
            }
            node = null;

        }else if(null != node.getLeft() || null != node.getRight()){
            // 只有一个子节点
            removeSingleSon(node);
            node = null;
        }else{
            // 无子节点但是黑色，需重新修正红黑树
            removeRebuildRb(node,node.getParent());
            //黑色处理完删除！红色直接删除
            if (node.getFather().getLeft() == node){
                node.getFather().setLeft(null);
            }else{
                node.getFather().setRight(null);
            }
        }
    }

    /***
     * 删除只有一个儿子节点时
     * -替换被删除节点的颜色
     * -替换被删除节点的位置
     * -绑定关系
     */
    private void removeSingleSon(RbNodeInteger removeNode){
        if (null != removeNode.getLeft()){
            removeNode.getLeft().getGrandFather().setLeft(removeNode.getLeft());
            removeNode.getLeft().setParent(removeNode.getParent());
            removeNode.getLeft().setRed(removeNode.isRed());
        }else if (null != removeNode.getRight()){
            removeNode.getRight().getGrandFather().setRight(removeNode.getRight());
            removeNode.getRight().setParent(removeNode.getParent());
            removeNode.getRight().setRed(removeNode.isRed());
        }
    }

    /****
     * 删除以后，重新构建一颗红黑树
     * 1-删除的是一个单独的黑色节点，那么删除后，两边的黑色节点的数量很有可能不一致
     *
     */
    private RbNodeInteger removeRebuildRb(RbNodeInteger node,RbNodeInteger parent){
        RbNodeInteger bro = null;
        while (null == node || !node.isRed()){
            if (node == parent.getLeft()){
                bro = parent.getRight();
                if (bro != null && bro.isRed()){
                    //情况一：要删除的节点为黑色，且在左边，兄弟节点在右边，红色
                    //，兄弟节点左旋
                    setBlack(bro);
                    setRed(parent);
                    leftRotate(bro);
                    bro = parent.getRight();
                }
                //情形2，兄弟置换兄弟节点和父节点的颜色节点为黑色;兄弟两个子节点都为黑色;
                if ((bro.getLeft() == null || !bro.getLeft().isRed())
                        && (bro.getRight() == null || !bro.getRight().isRed())){
                    setRed(bro);
                    node = parent;
                    parent = parent.getFather();
                }else{
                    //情形3：删除的节点是黑色；兄弟节点是黑色；兄弟节点左孩子红色；兄弟节点右孩子黑色
                    if ((bro.getRight() == null || !bro.getRight().isRed())){
                        //兄弟节点的左孩子是红色的，右节点是黑色
                        setBlack(bro.getLeft());
                        setRed(bro);
                        rightRotate(bro.getLeft());
                        bro = parent.getRight();
                    }

                    //情形4:删除的节点是黑色;兄弟节点是黑色的；并且兄弟节点右孩子是红色，左孩子任意颜色
                    bro.setRed(bro.getFather().isRed());
                    setBlack(bro.getFather());
                    setBlack(bro.getRight());
                    leftRotate(bro);
                    node = this.root;
                    break;
                }
            }else{
                bro = parent.getLeft();
                if (bro != null && bro.isRed()){
                    //情况一：要删除的节点为黑色，且在左边，兄弟节点在右边，红色
                    //，兄弟节点左旋
                    setBlack(bro);
                    setRed(parent);
                    leftRotate(bro);
                    bro = parent.getRight();
                }
                //情形2，兄弟置换兄弟节点和父节点的颜色节点为黑色;兄弟两个子节点都为黑色;
                if ((bro.getLeft() == null || !bro.getLeft().isRed())
                        && (bro.getRight() == null || !bro.getRight().isRed())){
                    setRed(bro);
                    node = parent;
                    parent = parent.getFather();
                }else{
                    //情形3：删除的节点是黑色；兄弟节点是黑色；兄弟节点左孩子红色；兄弟节点右孩子黑色
                    if ((bro.getRight() == null || !bro.getRight().isRed())){
                        //兄弟节点的左孩子是红色的，右节点是黑色
                        setBlack(bro.getLeft());
                        setRed(bro);
                        rightRotate(bro.getLeft());
                        bro = parent.getRight();
                    }

                    //情形4:删除的节点是黑色;兄弟节点是黑色的；并且兄弟节点右孩子是红色，左孩子任意颜色
                    bro.setRed(bro.getFather().isRed());
                    setBlack(bro.getFather());
                    setBlack(bro.getRight());
                    rightRotate(bro);
                    node = this.root;
                    break;
                }

            }
        }

        setBlack(node);
        return node;
    }

    /**
     * 左旋
     * 右右类型传入父亲节点
     * 左右类型传入儿子节点
     * @param node 传入的永远是要变成父节点的子节点，
     * @return 返回最新的父节点
     */
    private RbNodeInteger leftRotate(RbNodeInteger node){
        RbNodeInteger grandFather = node.getGrandFather();
        RbNodeInteger father = node.getFather();

        // 双方丢失的节点重新绑定
        father.setRight(node.getLeft());

        // 绑定爷爷和父亲的角色
        if (grandFather != null){
            //到root节点，则重新赋值root
            if (father == grandFather.getLeft()){
                grandFather.setLeft(node);
            }else{
                grandFather.setRight(node);
            }
            node.setParent(grandFather);
        }else{
            root = node;
            node.setParent(null);
        }

        node.setLeft(father);
        father.setParent(node);
        return node;
    }
    /**
     * 右旋
     * 右右类型传入父亲节点
     * 左右类型传入儿子节点
     * @param node 传入的永远是要变成父节点的子节点，
     * @return 返回最新的父节点
     */
    private RbNodeInteger rightRotate(RbNodeInteger node) {
        RbNodeInteger grandFather = node.getGrandFather();
        RbNodeInteger father = node.getFather();

        // 双方丢失的节点重新绑定
        father.setLeft(node.getRight());

        // 绑定爷爷和父亲的角色
        if (grandFather != null){
            //到root节点，则重新赋值root
            if (father == grandFather.getLeft()){
                grandFather.setLeft(node);
            }else{
                grandFather.setRight(node);
            }
            node.setParent(grandFather);
        }else{
            this.root = node;
            node.setParent(null);
        }

        node.setRight(father);
        father.setParent(node);
        return node;
    }

    private void setBlack(RbNodeInteger node){
        if (null != node){
            node.setRed(false);
        }
    }

    private void setRed(RbNodeInteger node){
        if (null != node){
            node.setRed(true);
        }
    }

    /***
     * 从父节点及其子节点中找value值
     * @param father
     * @param val
     * @return
     */
    private RbNodeInteger findValue(RbNodeInteger father,int val){
        while (father != null){
            if (val > father.getVal()){
                father = father.getRight();
            }else if (val < father.getVal()){
                father = father.getLeft();
            }else{
                return father;
            }
        }
        return null;
    }

    private void redBlackBlack(RbNodeInteger node){
        setRed(node);
        setBlack(node.getLeft());
        setBlack(node.getRight());
    }

    private void blackRedRed(RbNodeInteger node){
        setBlack(node);
        setRed(node.getLeft());
        setRed(node.getRight());
    }
}
