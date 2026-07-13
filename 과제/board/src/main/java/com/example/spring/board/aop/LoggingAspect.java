package com.example.spring.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.spring.board.controller..*(..))")
    public void controllerLayer() {}

    @Around("controllerLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = joinPoint.getSignature().toShortString();
        long start = System.currentTimeMillis();

        System.out.println("===> 시작: " + name);
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long took = System.currentTimeMillis() - start;
            System.out.println("<=== 종료: " + name + "(" + took + ")");
        }
    }
}
