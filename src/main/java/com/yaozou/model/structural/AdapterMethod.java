package com.yaozou.model.structural;

import lombok.Data;

/**
 * 适配器模式
 * 要分为三类：类的适配器模式、对象的适配器模式、接口的适配器模式。
 */
public class AdapterMethod {
    public static void main(String[] args){
        //类的适配器模式
        ClassAdapter classAdapter = new ClassAdapter();
        classAdapter.run();
        //对象的适配器模式
        ObjectAdapter objectAdapter = new ObjectAdapter();
        objectAdapter.run();
        //接口的适配器模式
        InterfaceAdapter interfaceAdapter = new InterfaceAdapter();
        interfaceAdapter.run();
    }
}

interface Targetable{
    void methodFirst();
    void methodSecond();
}
class Source{
    public void methodFirst(){
        System.out.println("This a first method.");
    }
}
/**
 *  类的适配器模式
 *  核心思想就是：有一个Source类，拥有一个方法，待适配，目标接口时Targetable，通过Adapter类，将Source的功能扩展到Targetable里
 */
class ClassAdapter{
    class Adapter extends Source implements Targetable{
        public void methodSecond() {
            System.out.println("This a second method.");
        }
    }

    public void run(){
        Targetable targetable = new Adapter();
        targetable.methodFirst();
        targetable.methodSecond();
    }
}

/**
 * 对象的适配器模式
 * 基本思路和类的适配器模式相同，只是将Adapter类作修改，这次不继承Source类，而是持有Source类的实例，以达到解决兼容性的问题。
 */
class ObjectAdapter{
    @Data
    class Adapter implements Targetable{
        private Source source;
        public Adapter(Source source){
            super();
            this.source = source;
        }

        public void methodFirst() {
            source.methodFirst();
        }

        public void methodSecond() {
            System.out.println("This a second method.");
        }

    }
    public void run(){
        Targetable targetable = new Adapter(new Source());
        targetable.methodFirst();
        targetable.methodSecond();
    }
}

/**
 * 接口的适配器模式
 */
class InterfaceAdapter{
    abstract class AbstractClass implements Targetable{
        public void methodFirst() { }

        public void methodSecond(){ }
    }

    class SourceSubFirst extends AbstractClass{
        @Override
        public void methodFirst() {
            System.out.println("This a first method.");
        }
    }

    class SourceSubSecond extends AbstractClass{
        @Override
        public void methodSecond() {
            System.out.println("This a second method.");
        }
    }

    public void run(){
        Targetable targetable1 = new SourceSubFirst();
        Targetable targetable2 = new SourceSubSecond();

        targetable1.methodFirst();
        targetable1.methodSecond();

        targetable2.methodFirst();
        targetable2.methodSecond();
    }
}
