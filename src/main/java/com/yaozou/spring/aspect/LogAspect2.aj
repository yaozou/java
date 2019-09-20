package com.yaozou.spring.aspect;

public aspect LogAspect2 {
    /**
     * 定义切点,日志记录切点
     */
    pointcut recordLog():call(* Test.sayHello(..));

    /**
     * 定义切点,权限验证(实际开发中日志和权限一般会放在不同的切面中,这里仅为方便演示)
     */
    pointcut authCheck():call(* Test.sayHello(..));

    /**
     * 定义前置通知!
     */
    before():authCheck(){
        System.out.println("sayHello方法执行前验证权限");
    }

    /**
     * 定义后置通知
     */
    after():recordLog(){
        System.out.println("sayHello方法执行后记录日志");
    }

    /**
     * 环绕通知 可通过proceed()控制目标函数是否执行
     * Object around(参数):连接点函数{
     *     函数体
     *     Object result=proceed();//执行目标函数
     *     return result;
     * }
     */
    Object around():aroundAdvice(){
        System.out.println("sayAround 执行前执行");
        Object result = proceed();//执行目标函数
        System.out.println("sayAround 执行后执行");
        return result;
    }
}
