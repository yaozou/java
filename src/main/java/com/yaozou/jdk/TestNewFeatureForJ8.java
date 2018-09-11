package com.yaozou.jdk;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: JAVA8新特性
 * @Auther: yaozou
 * @Date: 2018/8/29 10:32
 */
public class TestNewFeatureForJ8 {
    public static void main(String[] args) {
        list();
    }

    // Lambda表达式
    public static  void lambda(){
        TestNewFeatureForJ8.create((t1,t2) -> new TestNewFeatureForJ8());
    }

    // 流式 list 转 map
    public static void listToConcurrentMap(){
        List<TestCollectorBean> list = new ArrayList<>();
        list.add(new TestCollectorBean("1","1"));
        list.add(new TestCollectorBean("1","2"));

        Map<String,TestCollectorBean> map1 = list.stream().collect(Collectors.toConcurrentMap(TestCollectorBean::getName, a->a,(k1, k2)->k1)); //当key发生重复时取第一次的value
        Map<String,TestCollectorBean> map2 = list.stream().collect(Collectors.toConcurrentMap(TestCollectorBean::getName,a->a,(k1,k2)->k2)); //当key发生重复时取第二次的value
        //Map<String,TestBean> map3 = list.stream().collect(Collectors.toConcurrentMap(TestCollectorBean::getName,a->a)); //当key发生重复时抛出异常
        System.out.println("k1:"+map1.get("1"));
        System.out.println("k2:"+map2.get("1"));
        //System.out.println("k2:"+map3.get("1"));
    }

    public static void list(){
        List<Integer> list = Arrays.asList(1, 2, 4, 5, 3, 6, 7, 8, 9);
        Collections.sort(list,(s1,s2) -> s1.compareTo(s2));
        list.stream().filter(integer -> integer > 2).forEach(System.out::print);
    }

    public static void optional(){
        Optional.ofNullable((new String()));
    }

    public static void javaScript(){
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

        String name = "Runoob";
        Integer result = null;
        try {
            nashorn.eval("print('" + name + "')");
            result = (Integer) nashorn.eval("10 + 2");
        }catch(ScriptException e){
            System.out.println("执行脚本错误: "+ e.getMessage());
        }

        System.out.println(result.toString());
    }

    public static void localDateTime (){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate date1 = currentTime.toLocalDate();

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();

        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        LocalTime date4 = LocalTime.of(22, 15);
        LocalTime date5 = LocalTime.parse("20:15:30");

        // 获取当前时间日期 (时区)
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
        ZoneId id = ZoneId.of("Europe/Paris");
        ZoneId currentZone = ZoneId.systemDefault();
    }

    public static void create(TestNewFeature testNewFeature){
        System.out.println("Do something........");
    }
}

interface TestNewFeature {
    TestNewFeatureForJ8 func(int t1 , int t2);
}







