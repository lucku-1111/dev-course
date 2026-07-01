package AOP;

import AOP.service.MemberService;
import AOP.service.OrderService;
import AOP.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {
        var context = new AnnotationConfigApplicationContext(AopConfig.class);

        OrderService orderService = context.getBean(OrderService.class);
        MemberService memberService = context.getBean(MemberService.class);
        ProductService productService = context.getBean(ProductService.class);

        System.out.println("===== 주문 서비스 호출 =====");
        System.out.println(orderService.placeOrder("기계식 키보드"));

        System.out.println("\n===== 회원 서비스 호출 =====");
        System.out.println(memberService.register("kim"));

        System.out.println("\n===== 진짜 프록시인지 확인 =====");
        System.out.println(productService.getProduct("A-100"));

        System.out.println("\n===== 진짜 프록시인지 확인 =====");
        System.out.println("orderService 의 실제 타입: " + orderService.getClass());

        context.close();
    }
}
