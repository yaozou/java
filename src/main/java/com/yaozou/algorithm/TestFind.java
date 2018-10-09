package com.yaozou.algorithm;

import lombok.Data;

/**
 * @Description:
 * @Auther: yaozou
 * @Date: 2018/10/8 15:46
 */
public class TestFind {

    public static void main(String[] args){

    }

    static int find(int[] arr, int num , int value){
        if(arr == null || num == 0){
            return -1;
        }
        for(int index = 0;index < num;index++){
            if(value == arr[index]){
                return index;
            }
        }
        return -1;
    }

    static int binary_find(int[] arr, int num , int value){
        if(arr == null || num == 0){
            return -1;
        }
        int start = 0;
        int end   = num-1;
        while (start <= end){
            int middle = start+((end-start) >> 1);
            if(value == arr[middle]){
                return middle;
            }
            else if(value > arr[middle]){
                start = middle + 1;
            }
            else {
                end = middle - 1;
            }
        }
        return -1;
    }

    static Node binarytree_find(Node pNode , int value){
        if (pNode == null){
            return null;
        }
        if (value == pNode.getData()){
            return pNode;
        }
        else if (value < pNode.getData()){
            return binarytree_find(pNode.getLeft(),value);
        }
        else{
            return binarytree_find(pNode.getRight(),value);
        }
    }
}

@Data
class Node{
    private int data;
    private Node left;
    private Node right;
}
