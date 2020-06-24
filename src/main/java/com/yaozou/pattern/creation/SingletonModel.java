package com.yaozou.pattern.creation;

/**
 * 单例模式
 * 单例对象（Singleton）是一种常用的设计模式。在Java应用中，单例对象能保证在一个JVM中，
 * 该对象只有一个实例存在。这样的模式有几个好处：
 1、某些类创建比较频繁，对于一些大型的对象，这是一笔很大的系统开销。
 2、省去了new操作符，降低了系统内存的使用频率，减轻GC压力。
 3、有些类如交易所的核心交易引擎，控制着交易流程，如果该类可以创建多个的话，系统完全乱了。
 （比如一个军队出现了多个司令员同时指挥，肯定会乱成一团），所以只有使用单例模式，才能保证核心交易服务器独立控制整个流程。
 */
public class SingletonModel {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static SingletonModel model = null;
    /* 私有构造方法，防止被实例化 */
    private SingletonModel(){}
    public static SingletonModel getInstance(){
        if (model == null) syncInit();
        return model;
    }
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return model;
    }
    private synchronized static void syncInit(){
        if (model == null) model = new SingletonModel();
    }
    public void run(){
        System.out.println("This is a singleton.");
    }
    public static void main(String[] args) {
        SingletonModel model = SingletonModel.getInstance();
        model.run();
    }
}
