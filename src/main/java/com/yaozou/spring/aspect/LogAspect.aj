package com.yaozou.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/3/8 10:55 
 */
@Aspect
public class LogAspect {
    @Pointcut("@annotation(com.yaozou.spring.annotation.Log)")
    public void pointcut(){
    }
    @Before("pointcut()")
    public  void  before(JoinPoint point){
        System.out.println("【前置通知】");
    }
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        try{
            System.out.println("【环绕通知---前置通知】");
            // 执行方法
            Object result = point.proceed();
            System.out.println("【环绕通知---后置通知（返回）】");
            return result;
        }catch (Throwable t){
            System.out.println("【环绕通知---后置通知（异常）】");
            throw t;
        }
    }




    @After("pointcut()")
    public void after(JoinPoint point){
        System.out.println("【后置通知】");
    }
    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint point){
        System.out.println("【后置-返回通知】");
    }
    @AfterThrowing("pointcut()")
    public void afterThrowing(JoinPoint point){
        System.out.println("【后置-异常通知】");
    }
}
