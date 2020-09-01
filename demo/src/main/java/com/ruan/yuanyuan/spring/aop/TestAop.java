package com.ruan.yuanyuan.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestAop
 * @Author ruanyuanyuan
 * @Date 2020/9/1-10:13
 * @Version 1.0
 * @Description TODO 定义AOP切面类
 **/
@Aspect
@Component
public class TestAop {

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     */
    @Pointcut("execution(* com.ruan.yuanyuan.spring.service.impl..*.*(..))")
    public void pointcut() {

    }

    /**
     * 前置通知：在目标方法执行前调用
     */
    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("==@Before== ruan.yuanyuan logger : before");
    }

    /**
     * 后置通知：在目标方法执行后调用，若目标方法出现异常，则不执行
     */
    @AfterReturning("pointcut()")
    public void afterReturning(JoinPoint joinPoint) {
        System.out.println("==@AfterReturning== ruan.yuanyuan logger : after returning");
    }

    /**
     * 后置/最终通知：无论目标方法在执行过程中出现异常都会在它之后调用
     */
    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("==@After== ruan.yuanyuan logger : finally returning");
    }

    /**
     * 异常通知：目标方法抛出异常时执行
     */
    @AfterThrowing("pointcut()")
    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("==@AfterThrowing== ruan.yuanyuan logger : after throwing");
    }

    /**
     * 环绕通知：灵活自由的在目标方法中切入代码
     */
    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 获取方法传入参数
        Object[] params = joinPoint.getArgs();
        joinPoint.proceed();
    }

}
