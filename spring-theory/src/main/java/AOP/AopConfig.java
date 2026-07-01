package AOP;

import AOP.service.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public Advisor advisor() {
        return new DefaultPointcutAdvisor(pointcut(), advice());
    }

    @Bean
    public AspectJExpressionPointcut pointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* AOP.service..*.*(..))");
        return pointcut;
    }

    @Bean
    public PerformanceMonitorAdvice advice() {
        return new PerformanceMonitorAdvice();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl();
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl();
    }

    @Bean
    public ProductService productService() {
        return new ProductServiceImpl();
    }
}
