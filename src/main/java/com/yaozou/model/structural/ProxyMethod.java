package com.yaozou.model.structural;/**
 * created by yaozou on 2018/4/20
 */

/**
 * 代理模式
 * @author yaozou
 * @create 2018-04-20 15:40
 **/
public class ProxyMethod {
    public static void main(String[] args) {
        // 多一个代理类出来，替原对象进行一些操作。比如：有的时候打官司，我们需要请律师，因为律师在法律方面有专长，可以替我们进行操作，表达我们的想法。
        /**
         *  应用场景：
         *      如果已有的方法在使用的时候需要对原有的方法进行改进，此时有两种办法：
         *          1、修改原有的方法来适应。这样违反了“对扩展开放，对修改关闭”的原则(开闭原则)。
         *          2、就是采用一个代理类调用原有的方法，且对产生的结果进行控制。这种方法就是代理模式。
         *           使用代理模式，可以将功能划分的更加清晰，有助于后期维护！
         */


        ProxyMethod method = new ProxyMethod();
        method.run();
    }

    interface Sourceable{
        void method();
    }
    class Source implements Sourceable{
        @Override
        public void method() {
            System.out.println("this is original method");
        }
    }
    class Proxy implements Sourceable{
        private Source source;
        public Proxy(){
            super();
            this.source = new Source();
        }

        @Override
        public void method() {
            System.out.println("after proxy!");
            source.method();
            System.out.println("before proxy!");
        }
    }

    public void run(){
        Proxy proxy = new Proxy();
        proxy.method();
    }
}
