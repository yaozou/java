package com.yaozou.algorithm;

/**
 * LFU(Least Frequently Used):最不常使用策略，在一段时间内，数据被使用频率次最少的，优先淘汰
 * 最少使用（LFU）是一种用于管理计算机内存的缓存算法。
 * 主要是记录和追踪内存块的使用次数，当缓存已满并且需要更多空间时，系统将以最低内存块使用频率清除内存.
 * 采用LFU算法的最简单方法是为每个加载到缓存的块分配一个计数器。
 * 每次引用该块时，计数器将增加一。
 * 当缓存达到容量并有一个新的内存块等待插入时，系统将搜索计数器最低的块并将其从缓存中删除。
 *
 * LRU全称 "Least Recently Used"，最近最少使用策略，判断最近被使用的时间，距离目前最远的数据优先被淘汰，
 * 作为一种根据访问时间来更改链表顺序从而实现缓存淘汰的算法，它是redis采用的淘汰算法之一
 *
 * created on 2021/3/24 9:28
 *
 * @author yaozou
 * @since v1.0.0
 */

import java.util.*;

/**
 * 思路：
 * 在链表的开始插入元素，然后每插入一次计数一次，接着按照次数重新排序链表，
 * 如果次数相同的话，按照插入时间排序，然后从链表尾部选择淘汰的数据
 */
public class LFU<K,V> {
    /** 总容量 */
    private int capacity;
    /** 所有的node节点 */
    private Map<K,Node> caches;

    public LFU(int size){
        this.capacity = size;
        this.caches   = new LinkedHashMap<>(size);
    }

    public void put(K key,V value){
        Node node = caches.get(key);
        if (node == null){
            if (caches.size() >= capacity){
                K leastKey = removeLeastCount();
                caches.remove(leastKey);
            }
            // 创建新节点
            node = new Node(key,value);
            caches.put(key,node);
        }
        // 已经存在的元素覆盖旧值
        else{
            node.value = value;
            node.setCount(node.getCount()+1);
            node.setTime(System.nanoTime());
        }

        // 排序
        sort();
    }

    public V get(K key){
        Node node = caches.get(key);
        if (node != null){
            node.setCount(node.getCount()+1);
            node.setTime(System.nanoTime());
            return (V)node.value;
        }
        return null;
    }


    private void sort(){
        List<Map.Entry<K,Node>> list = new ArrayList<>(caches.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
    }

    /**
     *  移除统计数或者时间比较最小的节点
     */
    private K removeLeastCount(){
        Collection<Node> values = caches.values();
        Node min = Collections.min(values);
        return (K)min.key;
    }

    class Node implements Comparable<Node>{
        Object key; /** 键 */
        Object value; /** 值 */
        long   time; /** 访问时间 */
        int    count; /** 访问次数 */

        public Node(Object key,Object value){
            this.key = key;
            this.value = value;
            this.time  = System.nanoTime();
            this.count = 1;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public long getTime() {
            return time;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        @Override
        public int compareTo(Node o) {
            int compare = Integer.compare(this.count,o.count);
            if (compare == 0){
                compare = Long.compare(this.time,o.time);
            }
            return compare;
        }
    }




}


