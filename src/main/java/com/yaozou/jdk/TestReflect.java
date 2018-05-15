package com.yaozou.jdk;/**
 * created by yaozou on 2018/5/15
 */

import lombok.Data;

/**
 * 反射机制
 * @author yaozou
 * @create 2018-05-15 15:43
 **/
@Data
public class TestReflect implements TestReflectImpl{
    private String nameVal;
    int ageVal;
    public TestReflect(){}
    public TestReflect(String nameVal){
        this.nameVal = nameVal;
    }
    public TestReflect(String nameVal,int ageVal){
        this.nameVal = nameVal;
        this.ageVal = ageVal;
    }
    public void sayChina() {
        System.out.println("hello ,china");
    }
    public void sayHello(String name, int age) {
        System.out.println(name+"  "+age);
    }
}
interface TestReflectImpl{
    public static final String name="name";
    public static  int age=20;
    public void sayChina();
    public void sayHello(String name, int age);
}

