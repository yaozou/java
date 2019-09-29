package com.yaozou.spring.config;

import com.yaozou.spring.bean.TestBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

public class SpringBeanPostProcessorOne implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + " BeanPostProcessor 第"+getOrder()+"被调用");
        if (bean instanceof TestBean){
            ((TestBean) bean).setTestName("BeanPostProcessorOne");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TestBean){
            ((TestBean) bean).setNum(getOrder());
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
