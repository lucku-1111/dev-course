package com.example.spring.springtheory.ch01.IoC;

public class Main {
    static void main(String[] args) {
        System.out.println("===== 2. DI: 제어를 바깥(main)으로 =====");
        new CoffeeMaker(new ColombiaBean()).brew();
        new CoffeeMaker(new EthiopiaBean()).brew();

        System.out.println("\n===== 3. IoC 컨테이너: 조립까지 위임 =====");
        CoffeeMaker maker = new CoffeeContainer().getCoffeeMaker();
        maker.brew();

        System.out.println("\n===== 4. 헐리우드 원칙: 흐름의 역전 =====");
        Button button = new Button();
        button.setListener(new LikeAction());
        button.press();
    }
}
