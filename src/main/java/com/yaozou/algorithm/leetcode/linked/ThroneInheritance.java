package com.yaozou.algorithm.leetcode.linked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created on 2020/9/27 11:32
 *
 * @author yaozou
 * @since v1.0.0
 */
public class ThroneInheritance {

    public static void main(String[] args) {
        /** ["ThroneInheritance","birth","death","birth","getInheritanceOrder","birth","death","getInheritanceOrder"]
         [["king"],["king","clyde"],["king"],["clyde","shannon"],[null],["shannon","scott"],["clyde"],[null]]*/
        /**["ThroneInheritance","birth","getInheritanceOrder","death","birth","getInheritanceOrder","birth","birth","getInheritanceOrder"]
         [["king"],["king","clyde"],[null],["clyde"],["king","shannon"],[null],["king","scott"],["scott","keith"],[null]]
         */
        /***["ThroneInheritance","getInheritanceOrder","birth","birth","birth","birth","getInheritanceOrder","birth","getInheritanceOrder"]
         [["king"],[null],["king","clyde"],["clyde","shannon"],["shannon","scott"],["king","keith"],[null],["clyde","joseph"],[null]]
         */
        ThroneInheritance t= new ThroneInheritance("king");
        t.birth("king", "clyde");
        t.birth("clyde","shannon");
        t.birth("shannon","scott");
        t.birth("king","keith");
        t.birth("clyde","joseph");
        t.getInheritanceOrder();
        // king->clyde->shannon->scott->joseph->keith
    }


    List<String> list = new ArrayList<>();
    /** key 名字 value 崽得个数 */
    Map<String,Integer> parents = new HashMap<>();
    String boss;

    public ThroneInheritance(String kingName) {
        // 大Boss
        list.add(kingName);
        boss = kingName;
    }

    public void birth(String parentName, String childName) {
        int num = 0;
        if(parents.containsKey(parentName)){
            num = parents.get(parentName);
        }
        int parent = 0;
        int middle = 0;
        for (int i = 0;i<list.size();i++){
            if (parentName.equals(list.get(i))){
                parent = i;
                if (parents.containsKey(parentName)){
                    middle = middle(i+1,i+parents.get(parentName),0);
                }
                break;
            }
        }
        int index = parent+middle+num+1;
        list.add(index,childName);
        parents.put(parentName,num+1);
    }


    private int middle(int start,int end,int num){
        for (;start<=end&& start<list.size();start++){
            if (parents.containsKey(list.get(start))){
                num = num+parents.get(list.get(start));
                num = middle(start+1,start+parents.get(list.get(start)),num);
            }
        }
        return num;
    }

    public void death(String name) {
        if(parents.containsKey(name)){
           int num = parents.get(name);
           if (num > 1){
               int newParentNameIndex = 0;
               for (int i = 0;i<list.size();i++){
                   if (name.equals(list.get(i))){
                       newParentNameIndex = i+1;
                       break;
                   }
               }
               parents.put(list.get(newParentNameIndex),num-1);
           }
           parents.remove(name);
        }
        list.remove(name);
    }

    public List<String> getInheritanceOrder() {
        return list;
    }
}

/**
 * Your ThroneInheritance object will be instantiated and called as such:
 * ThroneInheritance obj = new ThroneInheritance(kingName);
 * obj.birth(parentName,childName);
 * obj.death(name);
 * List<String> param_3 = obj.getInheritanceOrder();
 */
