package com.yaozou.jdk.proxy;/**
 * created by yaozou on 2018/5/15
 */

import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 反射机制
 * @author yaozou
 * @create 2018-05-15 15:43
 **/
@Data
public class TestReflect implements TestReflectImpl{
    private String nameVal;
    String ageVal;
    public TestReflect(String nameVal,String ageVal){
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
class TestReflectMain{
    public static void main(String[] args){
        try{
            Class clazz = Class.forName("com.yaozou.jdk.proxy.TestReflect");
            // 获得此类的构造方法
            Constructor<?>[] constructors =  clazz.getConstructors();
            System.out.println("constructors:"+constructors.length);
            Object[] params = null;
            Class[] paramsClazzs = null;
            if ((paramsClazzs=constructors[0].getParameterTypes()).length > 0){
                params =  new Object[paramsClazzs.length];
                int i = 0;
                for (Class paramsClazz:paramsClazzs) {
                    params[i] = paramsClazz.newInstance();
                    i++;
                }
            }
            //实例化
           Object obj = constructors[0].newInstance(params);
            //获得Field
            Field[] fields = clazz.getDeclaredFields();
            System.out.println("fields:"+fields.length);
            for (Field field:fields) {
                field.setAccessible(true);
                System.out.println("field:"+field.getName());
                field.set(obj,"aaaaa");
            }
            //获得所有方法
            Method[]  methods = clazz.getMethods();
            System.out.println("methods:"+methods.length);
            for (Method method:methods) {
                System.out.println("metthod:"+method.getName());
                if (method.getName().equals("sayHello")) {
                    Object[] paramMethods = null;
                    Type[] typeParams;
                    if ((typeParams = method.getParameterTypes()).length > 0){
                        int i = 0;
                        paramMethods = new Object[typeParams.length];
                        for (Type typeParam:typeParams ) {
                            System.out.println("type:"+typeParam.toString());
                            Object object = null;
                            if (typeParam instanceof Class){
                                object = "ueee";
                            }else if (typeParam.equals("int")) object = 1;
                            paramMethods [i] = object;
                            i++;
                        }
                    }
                    System.out.println("paramMethod:"+paramMethods.length);
                    method.invoke(obj,paramMethods);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

