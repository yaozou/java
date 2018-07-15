package com.yaozou.thread;/**
 * created by yaozou on 2018/6/28
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 *
 * @author yaozou
 * @create 2018-06-28 20:16
 **/
public class FutureTest {
    public static class Task implements Callable<String> {
        private int type;
        public Task(int type){
            this.type = type;
        }
        public String call() throws Exception {
            String val = new String();
            switch (type){
                case 0: val = getSimpleCardVO(); break;
                case 1: val = getAccountVO(); break;
                case 2: val =getSimpleCardVO(); break;
            }
            return val;
        }
    }

    public static String getSimpleProfileVO()throws Exception{
        Thread.sleep(3000L);
        System.out.println("getSimpleProfileVO");
        return "getSimpleProfileVO";
    }

    public static String getAccountVO() throws Exception{
        Thread.sleep(2000L);
        System.out.println("getAccountVO");
        return "getAccountVO";
    }

    public static String getSimpleCardVO()throws Exception{
        Thread.sleep(5000L);
        System.out.println("getSimpleCardVO");
        return "getSimpleCardVO";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService es = Executors.newFixedThreadPool(3);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; i++){
            results.add(es.submit(new Task(i)));
        }
        for (Future<String> future:results) {
            System.out.println("future:"+future.get());
        }
        //关闭线程池 不可以再 submit 新的 task，已经 submit 的将继续执行
        //es.shutdown();
        //关闭线程池 试图停止当前正在执行的 task，并返回尚未执行的 task 的 list
        List<Runnable> list = es.shutdownNow();
        for (Runnable r:list) {
            System.out.println(r.toString());
        }
        System.out.println("执行时间："+(System.currentTimeMillis()-start)+"ms");
    }
}
