package com.yaozou.pattern.creation;

/**
 * 工厂方法模式
 */
public class FactoryMethod {
    public static void main(String[] args) {
        /**
         * 普通工厂模式
         * 建立一个工厂类，对实现了同一接口的一些类进行实例的创建
         * 如：发送信息，可以短信/邮件/qq/微信等
         */
        SenderFactory factory = new SenderFactory();
        factory.produce("sms").send();
        /**
         * 多工厂模式
         * 对普通工厂方法模式的改进，在普通工厂方法模式中，
         * 如果传递的字符串出错，则不能正确创建对象，而多个工厂方法模式是提供多个工厂方法，
         * 分别创建对象
         */
        new MultiSenderFactory().produceQQ().send();

        /**
         * 静态工厂方法模式
         * 多个工厂方法模式里的方法置为静态的，不需要创建实例，直接调用
         */
        StaticSenderFactory.produceQQ().send();

        /**
         * 抽象工厂模式
         * 工厂方法模式有一个问题就是，类的创建依赖工厂类，也就是说，如果想要拓展程序，必须对工厂类进行修改，这违背了闭包原则。
         * 所以，从设计角度考虑，有一定的问题，如何解决？
         * 就用到抽象工厂模式，创建多个工厂类，这样一旦需要增加新的功能，直接增加新的工厂类就可以了，
         * 不需要修改之前的代码。
         */
        Sender sender = new QQProduce().produce();
        sender.send();
    }
}

interface Sender{
    void send();
}
class SmsSender implements Sender{
    public void send() {
        System.out.println("this is sms sender");
    }
}
class QQSender implements Sender{
    public void send() {
        System.out.println("this is QQ sender");
    }
}
class SenderFactory{
    public Sender produce(String type){
        if ("qq".equals(type)) return new QQSender();
        else if ("sms".equals(type)) return new SmsSender();

        return null;
    }
}

class MultiSenderFactory{
    public Sender produceQQ(){
        return new QQSender();
    }
    public Sender produceSms(){
        return new SmsSender();
    }
}

class StaticSenderFactory{
    public static Sender produceQQ(){
        return new QQSender();
    }
    public static Sender produceSms(){
        return new SmsSender();
    }
}

interface Provider{
    Sender produce();
}
class QQProduce implements Provider{
    public Sender produce() {
        return new QQSender();
    }
}
class SmsProduce implements Provider{
    public Sender produce() {
        return new SmsSender();
    }
}