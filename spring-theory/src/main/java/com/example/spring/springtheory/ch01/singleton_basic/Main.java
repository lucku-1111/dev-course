package com.example.spring.springtheory.ch01.singleton_basic;

public class Main {
    static void main() {
        System.out.println("===== 1. 싱글톤 없이: 번호표 두 대 (버그!) =====");
        NaiveTicketMachine a = new NaiveTicketMachine();
        NaiveTicketMachine b = new NaiveTicketMachine();

        System.out.println("A 기계가 발급: " + a.issue() + "번");
        System.out.println("B 기계가 발급: " + b.issue() + "번 <- 중복!");
        System.out.println("A 기계가 발급: " + a.issue() + "번");
        System.out.println("B 기계가 발급: " + b.issue() + "번 <- 또 중복!");

        System.out.println("\n===== 2. 싱글톤 적용: 번호표는 하나뿐 =====");
        TicketMachine w1 = TicketMachine.getInstance();
        TicketMachine w2 = TicketMachine.getInstance();

        System.out.println("1번 창구가 발급: " + w1.issue() + "번");
        System.out.println("2번 창구가 발급: " + w2.issue() + "번");
        System.out.println("1번 창구가 발급: " + w1.issue() + "번");
        System.out.println("2번 창구가 발급: " + w2.issue() + "번");
        System.out.println("같은 기계인가? " + (w1 == w2));

        System.out.println("\n===== 3. lazy 초기화 (설정 관리자) =====");
        Settings s1 = Settings.getInstance();
        Settings s2 = Settings.getInstance();

        s1.setTheme("dark");
        System.out.println("앱 설정 - 테마: " + s1.getTheme());
        System.out.println("앱 설정 - 테마: " + s2.getTheme() + " (어디서 불러도 같은 설정)");
        System.out.println("같은 설정 객체인가? " + (s1 == s2));
    }
}