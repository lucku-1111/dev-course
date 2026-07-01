package com.example.spring.springtheory.ch06.ex_6_3.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

// [InvocationHandler 방식(ex_6_1) vs Advice 방식(여기)의 핵심 차이]
//  1) target을 직접 갖지 않는다.
//     - InvocationHandler: 우리가 target 필드를 두고 method.invoke(target, args)로 직접 호출했다.
//     - Advice: invocation.proceed()만 호출하면 스프링이 알아서 다음 대상(target)을 실행해준다.
//       -> Advice는 '어떤 target인지' 몰라도 된다. 그만큼 더 범용적이고 재사용성이 높다.
//  2) '어떤 메서드에 적용할지'(메서드 선별)는 여기 없다.
//     - InvocationHandler: invoke() 안에서 method.getName().startsWith(pattern)으로 직접 걸렀다.
//     - Advice: 그 책임은 Pointcut으로 분리된다(아래 DaoFactory의 NameMatchMethodPointcut).
//       -> 부가기능(Advice)과 적용대상 선정(Pointcut)이 깔끔히 분리된다 = 재사용 극대화.
//  3) 예외가 그대로 전파된다.
//     - InvocationHandler: 리플렉션이라 InvocationTargetException으로 감싸여 와서 getTargetException()이 필요했다.
//     - Advice: invocation.proceed()는 target의 예외를 '그대로' 던진다(래핑 없음).
public class TransactionAdvice implements MethodInterceptor {

    private PlatformTransactionManager transactionManager;

    public TransactionAdvice(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object proceed = invocation.proceed();
            transactionManager.commit(status);

            return proceed;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
