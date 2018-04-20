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
 *  应用场景：将一个接口转换成满足另一个新接口的类时，可以使用类的适配器模式，创新一个新类，继承原有的类，实现新的接口。
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
 * 核心思想：基本思路和类的适配器模式相同，只是将Adapter类作修改，这次不继承Source类，而是持有Source类的实例，以达到解决兼容性的问题。
 * 应用场景：将一个对象转换成满足另一个新接口的对象时，可以创建一个Adapter类，持有原类的一个实例，在Adapter类的方法中，调用实例的方法
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
 * 核心思想：有时我们写的一个接口中有多个抽象方法，当我们写该接口的实现类时，必须实现该接口的所有方法，这明显有时比较浪费，
 *      因为并不是所有的方法都是我们需要的，有时只需要某一些，此处为了解决这个问题，我们引入了接口的适配器模式，借助于一个抽象类，
 *      该抽象类实现了该接口，实现了所有的方法，
 *      而我们不和原始的接口打交道，只和该抽象类取得联系，所以我们写一个类，继承该抽象类，重写我们需要的方法就行。
 * 应用场景：实现一个接口中所有的方法时，可以创建一个抽象类AbstractClass，实现所有方法，写别类时继承抽象类即可
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
