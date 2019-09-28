package com.yaozou.spring;

import com.yaozou.spring.web.RestTestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试spring
 * @author yaozou
 */
public class TestStartSpring {
    public static void main(String[] args) throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
        ((ClassPathXmlApplicationContext) applicationContext).refresh();
        RestTestController restTestController = applicationContext.getBean("restTestController",RestTestController.class);

    }
}
