package com.yaozou.jdk;/**
 * created by yaozou on 2018/4/20
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 集合类
 * @author yaozou
 * @create 2018-04-20 17:47
 **/
public class TestCollection {

    public void testMap(){
       Map hash = new HashMap();
       hash.put("aa","aaa");
       hash.put(null,null);
       hash.get("aa");
       hash.containsKey("aa");
       hash.containsValue("aaaa");

       Map tree = new TreeMap();
       tree.put("aa","aaa");

       Map linked = new LinkedHashMap();
       linked.put("aa","aaa");


       Map table = new Hashtable();
       table.put("aa","aaa");
       table.put(null,null);

       Map concurrentHashMap = new ConcurrentHashMap();
       concurrentHashMap.put("aa","aaa");
    }

    public void testList(){
        //顺序表
        List<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(11);

        Vector<Integer> vector = new Vector<Integer>();
        vector.add(11);

        //双向链表
        List<Integer> linkedList = new LinkedList<Integer>();
        linkedList.add(11);

    }
}
