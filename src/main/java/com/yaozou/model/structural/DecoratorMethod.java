package com.yaozou.model.structural;/**
 * created by yaozou on 2018/4/20
 */

/**
 * 装饰模式
 * @author yaozou
 * @create 2018-04-20 14:30
 **/
public class DecoratorMethod {
    public static void main(String[] args) {
        // 基本思想：装饰模式就是给一个对象增加一些新的功能，而且是动态的，要求装饰对象和被装饰对象实现同一个接口，装饰对象持有被装饰对象的实例
        // Source类是被装饰类，Decorator类是一个装饰类，可以为Source类动态的添加一些功能
        // 应用场景：1、需要扩展一个类的功能。
        //          2、动态的为一个对象增加功能，而且还能动态撤销。（继承不能做到这一点，继承的功能是静态的，不能动态增删。）
        //          缺点：产生过多相似的对象，不易排错！
        DecoratorMethod method = new DecoratorMethod();
        method.run();
    }

    interface Sourceable{
        void method();
    }

    class Source implements Sourceable{
        public void method() {
            System.out.println("This is a method");
        }
    }

    class Decorator implements Sourceable{
        private Sourceable source;
        public  Decorator(Sourceable source){
            this.source = source;
        }
        public void method() {
            System.out.println("Before Decorator......");
            source.method();
            System.out.println("After Decorator.......");
        }
    }

    public void run(){
        Sourceable source = new Source();
        Decorator decorator = new Decorator(source);
        decorator.method();
    }

}

