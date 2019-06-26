package com.yaozou.thread;/**
 * created by yaozou on 2018/6/28
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import lombok.Data;

/**
 *
 * @author yaozou
 * @create 2018-06-28 20:16
 **/
public class FutureTest {
    public static class Task implements Callable<String> {
        private int type;
        private FutureAttr futureAttr;
        public Task(int type,FutureAttr futureAttr){
            this.type = type;
            this.futureAttr = futureAttr;
        }
        @Override
        public String call() throws Exception {
            String val = new String();
            switch (type){
                case 0: val = getSimpleProfileVO(); break;
                case 1: val = getAccountVO(); break;
                case 2: val =getSimpleCardVO(); break;
            }
            return val;
        }
        public String getSimpleProfileVO()throws Exception{
            Thread.sleep(3000L);
            System.out.println("getSimpleProfileVO");
            this.futureAttr.setFirst(1);
            return "getSimpleProfileVO";
        }

        public String getAccountVO() throws Exception{
            Thread.sleep(2000L);
            System.out.println("getAccountVO");
            this.futureAttr.setSecond(2);
            return "getAccountVO";
        }

        public String getSimpleCardVO()throws Exception{
            Thread.sleep(5000L);
            System.out.println("getSimpleCardVO");
            this.futureAttr.setThird(3);
            return "getSimpleCardVO";
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService es = Executors.newFixedThreadPool(3);
        long start = System.currentTimeMillis();
        FutureAttr futureAttr = new FutureAttr();
        for (int i = 0; i < 3; i++){
            results.add(es.submit(new Task(i,futureAttr)));
        }
        for (Future<String> future:results) {
            System.out.println("future:"+future.get());
        }
        //关闭线程池 不可以再 submit 新的 task，已经 submit 的将继续执行
        es.shutdown();
        //关闭线程池 试图停止当前正在执行的 task，并返回尚未执行的 task 的 list
        /*List<Runnable> list = es.shutdownNow();
        for (Runnable r:list) {
            System.out.println(r.toString());
        }*/
        System.out.println("执行时间："+(System.currentTimeMillis()-start)+"ms");
        System.out.println("执行结果："+futureAttr.toString());

    }
}
@Data
class FutureAttr{
    private int first;
    private int second;
    private int third;
    @Override
    public String toString(){
        return first+"-"+second+"-"+third;
    }
}
