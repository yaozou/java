package com.yaozou.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试spring
 * @author yaozou
 */
public class TestStartSpring {
    public static void main(String[] args){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-mvc.xml");
    }
}
