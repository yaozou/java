package com.yaozou.jdk;/**
 * created by yaozou on 2018/4/20
 */

import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public void testSet(){
        Set<String> hashSet = new HashSet<String>();
        hashSet.add("aaa");
        Set<String> treeSet = new TreeSet<String>();
        treeSet.add("bbbb");
    }

    public void testCollection(){
        Integer[] intVal = {1,2,3,4,5,6,7,8,9,10};
        Arrays.sort(intVal);

        List<Integer> intList = Arrays.asList(intVal);
        Collections.sort(intList);

    }

    public static void main(String[] args){
        /*DecimalFormat decimalFormat = new DecimalFormat("###################.##");
        BigDecimal p2 = new BigDecimal(2.0D);
        BigDecimal p3 = new BigDecimal(2.00D);
        BigDecimal p4 = new BigDecimal(2.50D);
        BigDecimal p5 = new BigDecimal(2.585444D);
        System.out.println(p2.toString());
        System.out.println(p3.toString());
        System.out.println(p4.toString());
        System.out.println(p5.toString());

        System.out.println(decimalFormat.format(p2));
        System.out.println(decimalFormat.format(p3));
        System.out.println(decimalFormat.format(p4));
        System.out.println(decimalFormat.format(p5));*/

    }
}

