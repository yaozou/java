package com.yaozou.spring;

import com.yaozou.spring.bean.TestBean;
import com.yaozou.spring.service.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

/**
 * 测试spring
 * @author yaozou
 */
public class TestStartSpring {
    public static void main(String[] args) throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
        TestBean testBean = applicationContext.getBean("testBean",TestBean.class);
        System.out.println("result "+testBean.toString());
    }
}
