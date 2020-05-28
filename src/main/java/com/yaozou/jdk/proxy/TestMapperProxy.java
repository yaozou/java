package com.yaozou.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yaoozu
 * @description mybaits 的 mapper动态代理
 * @date 2020/5/2814:00
 * @since v1.0.0
 */
public class TestMapperProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(Object.class.equals(method.getDeclaringClass())){ //如果是Object中定义的方法，直接执行。如toString(),hashCode()等
            return method.invoke(this, args);
        }

        // 其他的mapper接口定义的方法交由MapperMethod
        return null;
    }
}
