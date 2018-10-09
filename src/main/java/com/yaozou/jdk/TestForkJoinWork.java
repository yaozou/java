package com.yaozou.jdk;

import lombok.Data;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @Description:
 *      并行流：把一个内容分成多个数据块，并用不同的线程分别处理每个数据块的流。（fork/join框架）
 *      串行流：则相反
 *      Fork/Join框架：
 *          在必要的情况下，将一个大的任务进行拆分(fork)成若干个子任务（拆到临界值），
 *          再将一个个任务的结果进行join汇总。
 *      Fork/Join与线程池的区别：
 *          Fork/Join采用“工作窃取模式”，当执行新的任务时可以将其拆分成更小的任务执行，
 *          并将小任务加到线程队列中。然后再随机从一个线程中偷一个任务并把他加入到自己的队列中。
 *          例如：CPU上有两个线程A/B，A已经执行完了，B还有任务未执行。这时A将B队尾的任务偷过来，
 *          加入到自己的队列中。相对于传统的线程池，Fork/Join更有效的使用了CPU资源。
 * @Auther: yaozou
 * @Date: 2018/10/9 15:25
 */
// ForkJoin这个框架的实现需要继承RecursiveTask 或者 RecursiveAction，
// RecursiveTask是有返回值的，相反RecursiveAction则没有
@Data
public class TestForkJoinWork extends RecursiveTask<Long> {
    private Long start;
    private Long end;
    public static final Long  CRITICAL = 10000L;

    public TestForkJoinWork(Long start , Long end){
        this.start = start;
        this.end   = end;
    }

    @Override
    protected Long compute() {
        Long length = end - start;
        if (length <= CRITICAL){
            Long sum = 0L;
            for (Long i = start;i<=end;i++){
                sum += i;
            }
            return sum;
        }else{
            Long middle = (start+end) / 2;
            TestForkJoinWork right = new TestForkJoinWork(start,middle);
            right.fork(); // 拆分 压入线程队列

            TestForkJoinWork left = new TestForkJoinWork(middle+1,end);
            left.fork(); // 拆分 压入线程队列

            return right.join() + left.join();
        }
    }
}

class TestForkJoinWorkDemo{

     void test1(){
        //Fork/Join实现
        long l = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();//实现ForkJoin 就必须有ForkJoinPool的支持
        TestForkJoinWork task = new TestForkJoinWork(0L,10000000000L);//参数为起始值与结束值
        Long invoke = forkJoinPool.invoke(task);
        long l1 = System.currentTimeMillis();
        System.out.println("invoke = " + invoke+"  time: " + (l1-l));
    }

    void test2(){
         // 普通线程完成
        Long x = 0L;
        Long y = 10000000000L;
        long l = System.currentTimeMillis();
        for (Long i = 0L; i <= y; i++) {
            x += i;
        }
        long l1 = System.currentTimeMillis();
        System.out.println("invoke = " + x+"  time: " + (l1-l));
    }

    void test3(){
         // 并行流
        long l = System.currentTimeMillis();
        //parallel()与sequential()在并行流与串行流中随意切换
        long reduce = LongStream.rangeClosed(0, 10000000000L).parallel().reduce(0, Long::sum);
        long l1 = System.currentTimeMillis();
        System.out.println("invoke = " + reduce+"  time: " + (l1-l));
    }

    public static void main(String[] args){
         TestForkJoinWorkDemo demo = new TestForkJoinWorkDemo();
         demo.test1();
         demo.test2();
         demo.test3();
        /**
         * invoke = -5340232216128654848  time: 40213
         * invoke = -5340232216128654848  time: 52510
         * invoke = -5340232216128654848  time: 963
         */
    }
}
