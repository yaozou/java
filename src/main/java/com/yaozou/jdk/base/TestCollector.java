package com.yaozou.jdk.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Auther: yaozou
 * @Date: 2018/8/23 10:00
 */
public class TestCollector {
    public static void main(String[] args){
        List<TestCollectorBean> list = new ArrayList<>();
        list.add(new TestCollectorBean("2",2));
        list.add(new TestCollectorBean("1",1));
        list.add(new TestCollectorBean("3",3));
        println(list);
        Collections.sort(list, new Comparator<TestCollectorBean>() {
            @Override
            public int compare(TestCollectorBean o1, TestCollectorBean o2) {
                return o1.getValue()-o2.getValue();
            }
        });
        println(list);


        //listToConcurrentMap(list);

    }

    public static void listToConcurrentMap(List<TestCollectorBean> list){
        Map<String,TestCollectorBean> map1 = list.stream().collect(Collectors.toConcurrentMap(TestCollectorBean::getName, a->a,(k1, k2)->k1)); //当key发生重复时取第一次的value
        Map<String,TestCollectorBean> map2 = list.stream().collect(Collectors.toConcurrentMap(TestCollectorBean::getName,a->a,(k1,k2)->k2)); //当key发生重复时取第二次的value
        //Map<String,TestBean> map3 = list.stream().collect(Collectors.toConcurrentMap(TestCollectorBean::getName,a->a)); //当key发生重复时抛出异常
        System.out.println("k1:"+map1.get("1"));
        System.out.println("k2:"+map2.get("2"));
        //System.out.println("k2:"+map3.get("1"));
    }

    public static void println(List<TestCollectorBean> list){
        System.out.println("-----------------------------");
        for(TestCollectorBean bean:list){
            System.out.print(bean.toString());
            System.out.println();
        }
    }
}

@Data
class TestCollectorBean{
    private String name;
    private int value;
    public TestCollectorBean(String name, int value){
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString(){
        return "name="+name+",value="+value;
    }
}
