package AOP;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;

public class PerformanceMonitorAdvice implements MethodInterceptor {
    @Override
    public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
        String name = invocation.getMethod().getDeclaringClass().getSimpleName()
                + "." + invocation.getMethod().getName();
        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long time = System.currentTimeMillis() - start;
            System.out.printf("[PERF] %s : %dms%n", name, time);
        }
    }
}
