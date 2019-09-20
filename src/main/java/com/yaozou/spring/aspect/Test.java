package com.yaozou.spring.aspect;

/**
 * @author yao.zou
 * @version V1.0
 * @description: TODO
 * @date 11:04
 */
public class Test {
    public void sayHello(){
        System.out.println("hello world !");
    }
    public static void main(String args[]){
        Test helloWord =new Test();
        helloWord.sayHello();
    }
}
