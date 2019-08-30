package com.yaozou.jdk.base;/**
 * created by yaozou on 2018/4/20
 */

import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集合类
 * @author yaozou
 * @create 2018-04-20 17:47
 **/
public class TestCollection {

    public void testMap(){
       Map hash = new HashMap(16);
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

       Map concurrentHashMap = new ConcurrentHashMap(16);
       concurrentHashMap.put("aa","aaa");

       Map synchronizedMap = Collections.synchronizedMap(new HashMap<>(8));

        Map<TestMapKey,TestMapKey> map = new HashMap(16);
        TestMapKey key1 = new TestMapKey("key","value");
        TestMapKey key2 = new TestMapKey("key","value");
        map.put(key1,key1);
        map.put(key2,key2);
        System.out.println(map.size());
        System.out.println(key1.toString()+":" + key2.toString());
        System.out.println(key1.hashCode()+":" + key2.hashCode());
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
        intList.sort(null);
        Collections.sort(intList);

    }

    public static void main(String[] args){
        Map concurrentHashMap = new ConcurrentHashMap(16);
        concurrentHashMap.put("aa","aaa");
        concurrentHashMap.put("bb","bbb");
        concurrentHashMap.put("cc","ccc");
        concurrentHashMap.forEach((vin, netType) -> {
            concurrentHashMap.remove(vin);
                });
        Object[] keys = concurrentHashMap.keySet().toArray();
        List list = Arrays.asList(keys);
        list.forEach(key->{
            concurrentHashMap.remove(key);
        });

//         static final int hash(Object key) {
//            int h;
//            return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
//         }
        Object obj = "a";
        int h = obj.hashCode();
        System.out.println("h="+h +":"+Integer.toBinaryString(h));
        // 右移16位即丢弃低16位，就是任何小于2^16的数，右移16后结果都为0
        // 任何一个数，与0按位异或的结果都是这个数本身
        // 大于等于2^16的时候才会重新调整其值。
        int hightH = h>>>16;
        // 使用hash值与hash的高16位进行异或运算防止哈希冲突太过频繁
        int hash = h ^ hightH;
        System.out.println("(h>>>16)="+hightH+":"+Integer.toBinaryString(hightH));
        System.out.println("hash="+hash+":"+Integer.toBinaryString(hash));

        /**
         * i = (n - 1) & hash 初始大小为什么是2的n次方
         */
        int n = 1<<4;
        int i = (n-1) & hash;
        System.out.println("(n-1)="+(n-1)+":"+Integer.toBinaryString((n-1)));
        System.out.println("i="+i);

        int a = 1;
        int b = 0;
        // 逻辑运算
        System.out.println("a&&b="+(a==0&&b==1));
        System.out.println("a||b="+(a==1||b==1));
        System.out.println("a^b="+(a==1^b==1));
        // 位运算
        System.out.println("1&1="+(a&a)+","+"1&0="+(a&b)+","+"0&0="+(b&b));
        System.out.println("1|1="+(a|a)+","+"1|0="+(a|b)+","+"0|0="+(b|b));
        System.out.println("1^1="+(a^a)+","+"1^0="+(a^b)+","+"0^0="+(b^b));
        // 左右移位、无符号右移位
        int num1 = 2,num2=-2;
        System.out.println("num1<<2="+(num1<<2)+","+"num1>>2="+(num2>>2));
        System.out.println("num1>>>2="+(num1>>>2)+","+"num2>>>2="+(num2>>>2));
    }
}

@Data
class TestMapKey{
    private String key;
    private String value;

    public TestMapKey(String key,String value){
        this.key = key;
        this.value = value;
    }
}

