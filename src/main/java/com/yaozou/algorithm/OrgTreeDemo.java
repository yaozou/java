package com.yaozou.algorithm;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author yaozou
 * @description: 组织机构 树形结构
 * @date 2019-11-22 20:08
 * @since 1.0.0
 */

public class OrgTreeDemo{
    public static void main(String[] args){
        Tree tree = new Tree(genOrgList());
        TreeNode treeNode = tree.getTreeNode("2");

        // 构造方法里，也可以直接传需要序列化的属性名字
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().add("parent");
        filter.getExcludes().add("allChildren");
        String data = JSONObject.toJSONString(treeNode, filter);
        System.out.println(data);
    }

    public static List<ITreeNode> genOrgList(){
        List<ITreeNode> list = new ArrayList<ITreeNode>();

        OrgTree org = new OrgTree("2", "1", "北京市", 2, "110000", "2");
        list.add(org);
        org = new OrgTree("3", "2", "市辖区", 3, "110100", "3");
        list.add(org);
        org = new OrgTree("4", "3", "东城区", 4, "110101", "4");
        list.add(org);
        org = new OrgTree("5", "3", "东城区", 5, "110102", "4");
        list.add(org);
        org = new OrgTree("6", "3", "东城区", 6, "110105", "4");
        list.add(org);
        org = new OrgTree("7", "3", "东城区", 7, "110106", "4");
        list.add(org);
        org = new OrgTree("8", "3", "东城区", 8, "110107", "4");
        list.add(org);
        org = new OrgTree("9", "3", "东城区", 9, "110108", "4");
        list.add(org);
        org = new OrgTree("10", "3", "东城区", 10, "110109", "4");
        list.add(org);
        org = new OrgTree("11", "3", "东城区", 11, "110111", "4");
        list.add(org);
        org = new OrgTree("12", "3", "东城区", 12, "110112", "4");
        list.add(org);
        org = new OrgTree("13", "3", "东城区", 13, "110113", "4");
        list.add(org);
        org = new OrgTree("14", "3", "东城区", 14, "110114", "4");
        list.add(org);
        org = new OrgTree("15", "3", "东城区", 15, "110115", "4");
        list.add(org);
        org = new OrgTree("16", "3", "东城区", 16, "110116", "4");
        list.add(org);
        org = new OrgTree("17", "3", "东城区", 17, "110117", "4");
        list.add(org);
        org = new OrgTree("18", "2", "县", 3, "110200", "3");
        list.add(org);
        org = new OrgTree("19", "18", "密云县", 19, "110228", "4");
        list.add(org);
        org = new OrgTree("20", "18", "延庆县", 20, "110229", "4");
        list.add(org);
        return list;
    }
}

@Data
class OrgTree implements ITreeNode {
    private String uuid;
    private String parentId;
    private String name;
    private Integer orderNum;
    private String code;
    private String type;

    public OrgTree(){

    }
    public OrgTree(String uuid, String parentId, String name, Integer orderNum, String code, String type){
        this.uuid = uuid;
        this.parentId = parentId;
        this.name = name;
        this.orderNum = orderNum;
        this.code = code;
        this.type = type;
    }

    @Override
    public String getNodeId() {
        return this.uuid;
    }

    @Override
    public String getNodeName() {
        return this.name;
    }

    @Override
    public String getNodeParentId() {
        return this.parentId;
    }
}

/**
 * 树节点
 */
@Data
class TreeNode{
    /** 树节点ID */
    @JSONField
    private String nodeId;
    /** 树节点名称 */
    @JSONField
    private String nodeName;
    /** 父节点ID */
    @JSONField
    private String parentNodeId;
    /** 节点在树中的排序号 */
    @JSONField
    private int orderNum;
    /** 节点所在的层级 */
    @JSONField
    private int level;
    private TreeNode parent;
    /** 当前节点的二子节点 */
    @JSONField
    private List<TreeNode> children = new ArrayList<>();
    /** 当前节点的子孙节点 */
    private List<TreeNode> allChildren = new ArrayList<TreeNode>();

    public TreeNode(ITreeNode obj){
        this.nodeId = obj.getNodeId();
        this.nodeName = obj.getNodeName();
        this.parentNodeId = obj.getNodeParentId();
        this.orderNum = obj.getOrderNum();
    }

    public void addChild(TreeNode treeNode){
        this.children.add(treeNode);
    }
    public void removeChild(TreeNode treeNode){
        this.children.remove(treeNode);
    }

    public List<TreeNode> getAllChildren() {
        if(this.allChildren.isEmpty()){
            for(TreeNode treeNode : this.children){
                this.allChildren.add(treeNode);
                this.allChildren.addAll(treeNode.getAllChildren());
            }
        }
        return this.allChildren;
    }
}

interface ITree{
    /**
     * 获得树形结构
     * @return
     */
     List<TreeNode> getTree();

    /**
     * 获得根节点
     * @return
     */
     List<TreeNode> getRoot();

    /**
     * 获得节点信息
     * @param nodeId 节点id
     * @return
     */
     TreeNode getTreeNode(String nodeId);
}

class Tree implements ITree {
    private HashMap<String, TreeNode> treeNodesMap = new HashMap<>();
    private List<TreeNode> treeNodesList = new ArrayList<>();

    public Tree(List<ITreeNode> list){
        initTreeNodeMap(list);
        initTreeNodeList();
    }

    private void initTreeNodeMap(List<ITreeNode> list){
        TreeNode treeNode = null;
        for(ITreeNode item : list){
            treeNode = new TreeNode(item);
            treeNodesMap.put(treeNode.getNodeId(), treeNode);
        }

        Iterator<TreeNode> iter = treeNodesMap.values().iterator();
        TreeNode parentTreeNode;
        while(iter.hasNext()){
            treeNode = iter.next();
            // 根节点
            if(treeNode.getParentNodeId() == null || treeNode.getParentNodeId() == ""){
                continue;
            }
            // 寻找父节点
            parentTreeNode = treeNodesMap.get(treeNode.getParentNodeId());
            if(parentTreeNode != null){
                treeNode.setParent(parentTreeNode);
                parentTreeNode.addChild(treeNode);
            }
        }
    }

    private void initTreeNodeList(){
        if(treeNodesList.size() > 0){
            return;
        }
        if(treeNodesMap.size() == 0){
            return;
        }
        Iterator<TreeNode> iter = treeNodesMap.values().iterator();
        TreeNode treeNode;
        while(iter.hasNext()){
            treeNode = iter.next();
            // 根节点
            if(treeNode.getParent() == null){
                this.treeNodesList.add(treeNode);
                this.treeNodesList.addAll(treeNode.getAllChildren());
            }
        }
    }

    @Override
    public List<TreeNode> getTree() {
        return this.treeNodesList;
    }

    @Override
    public List<TreeNode> getRoot() {
        List<TreeNode> rootList = new ArrayList<>();
        if (this.treeNodesList.size() > 0) {
            for (TreeNode node : treeNodesList) {
                if (node.getParent() == null){
                    rootList.add(node);
                }
            }
        }
        return rootList;
    }

    @Override
    public TreeNode getTreeNode(String nodeId) {
        return this.treeNodesMap.get(nodeId);
    }

}

interface ITreeNode {
    String getNodeId();
    String getNodeName();
    String getNodeParentId();
    Integer getOrderNum();
}