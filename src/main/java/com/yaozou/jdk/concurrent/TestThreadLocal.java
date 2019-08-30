package com.yaozou.jdk.concurrent;

/**
 * @Description: TODO
 * @Author yao.zou
 * @Date 2019/8/30 0030
 * @Version V1.0
 **/
class TestThreadLocal{
    private static TestThreadLocal testThreadLocal = null;
    private static ThreadLocal<TestThreadLocal> map = new ThreadLocal<TestThreadLocal>();
    private TestThreadLocal(){}
    public static synchronized TestThreadLocal getInstance1(){
        if(testThreadLocal==null){
            testThreadLocal = new TestThreadLocal();
        }
        return testThreadLocal;
    }
    public static TestThreadLocal getInstance2(){ //效率大于getInstance1()
        testThreadLocal = map.get();
        if(testThreadLocal==null){
            testThreadLocal = new TestThreadLocal();
            map.set(testThreadLocal);
        }
        return testThreadLocal;
    }
}
