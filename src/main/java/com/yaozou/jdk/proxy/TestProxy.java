package com.yaozou.jdk.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description:代理
 * JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 * CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
 * 因为是继承，所以该类或方法最好不要声明成final
 * @Auther: yaozou
 * @Date: 2018/9/12 10:52
 */
public class TestProxy {
    public static void main(String[] args){
        IUserManager userManager = (IUserManager) new CGLibProxy()
                .createProxyObject(new UserManagerImpl());
        System.out.println("-----------CGLibProxy-------------");
        userManager.addUser("tom", "root");
        System.out.println("-----------JDKProxy-------------");
        JDKProxy jdkPrpxy = new JDKProxy();
        IUserManager userManagerJDK = (IUserManager) jdkPrpxy
                .newProxy(new UserManagerImpl());
        userManagerJDK.addUser("kimi", "1234567");
    }
}

class CGLibProxy implements MethodInterceptor {

    /** CGLib需要代理的目标对象 */
    private Object targetObject;

    public Object createProxyObject(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        /** 返回代理对象 */
        return proxyObj;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        Object obj = null;
        if ("addUser".equals(method.getName())) {// 过滤方法
            checkPopedom();// 检查权限
        }
        obj = method.invoke(targetObject, args);
        return obj;
    }

    private void checkPopedom() {
        System.out.println(".:检查权限  checkPopedom()!");
    }
}

class JDKProxy implements InvocationHandler{
    /** 需要代理的目标对象 */
    private Object targetObject;

    public Object newProxy(Object targetObject) {//将目标对象传入进行代理
        this.targetObject = targetObject;
        /**返回代理对象*/
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /** 一般我们进行逻辑处理的函数 */
        checkPopedom();
        /** 设置方法的返回值 */
        Object ret = null;
        /** 调用invoke方法，ret存储该方法的返回值 */
        ret  = method.invoke(targetObject, args);
        return ret;
    }

    private void checkPopedom() {//模拟检查权限的例子
        System.out.println(".:检查权限  checkPopedom()!");
    }
}

interface IUserManager{
    void addUser(String id, String password);
}

class UserManagerImpl implements IUserManager{
    @Override
    public void addUser(String id, String password) {
        System.out.println(".: 掉用了UserManagerImpl.addUser()方法！ ");
        System.out.println("id:"+id+" password:"+password);
    }
}