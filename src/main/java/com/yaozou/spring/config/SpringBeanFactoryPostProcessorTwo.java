package com.yaozou.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

public class SpringBeanFactoryPostProcessorTwo implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor第" + getOrder() + "次被调动");
        BeanDefinition bd = configurableListableBeanFactory.getBeanDefinition("testBean");
        if (bd != null){
            System.out.println("testBean属性值:"+bd.getPropertyValues().toString());
            MutablePropertyValues pv = bd.getPropertyValues();
            if (pv.contains("testName")){
                System.out.println("修改testBean属性");
                pv.addPropertyValue("testName","BeanFactoryPostProcessorTwo");
            }
            System.out.println("修改testBean的作用域为prototype");
            bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}