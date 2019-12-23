package com.ruan.yuanyuan.proxy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName: OrderAspject
 * @author: ruanyuanyuan
 * @date: 2019/12/18 20:45
 * @version: 1.0
 * @description: 主要用于测试Spring的AOP原理调试
 **/
@Aspect
@Component
public class OrderAspect {

    private static Logger logger = LoggerFactory.getLogger(OrderAspect.class);

    //统一切点,对com.ruan.yuanyuan.service及其子包中所有的类的所有方法切面
    @Pointcut("execution(* com.ruan.yuanyuan.service.impl.*.*(..))")
    public void Pointcut() {

    }

    //前置通知
    @Before("Pointcut()")
    public void beforeMethod(JoinPoint joinPoint) {
        logger.info("前置通知");
    }

    //@After: 后置通知
    @After("Pointcut()")
    public void afterMethod(JoinPoint joinPoint) {
        logger.info("调用了后置通知");
    }

    //@AfterRunning: 返回通知 rsult为返回内容
    @AfterReturning(value = "Pointcut()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        logger.info("调用了返回通知");
    }


    //@AfterThrowing: 异常通知
    @AfterThrowing(value = "Pointcut()", throwing = "e")
    public void afterReturningMethod(JoinPoint joinPoint, Exception e) {
        logger.info("调用了异常通知");
    }

    //@Around：环绕通知
    @Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("around执行方法之前");
        Object object = pjp.proceed();
        logger.info("around执行方法之后--返回值：" + object);
        return object;
    }

}
