package com.example.spring.springtheory.ch01.SOLID_5원칙;

class DiscountCalculator {
    int calc(DiscountPolicy dp, int price) {
        return dp.discount(price);
    }
}

interface DiscountPolicy {
    int discount(int price);
}

class BasicDiscount implements DiscountPolicy {
    @Override
    public int discount(int price) {
        return price;
    }
}

class GoldDiscount implements DiscountPolicy {
    @Override
    public int discount(int price) {
        return price * 90 / 100;
    }
}

class VipDiscount implements DiscountPolicy {
    @Override
    public int discount(int price) {
        return price * 80 / 100;
    }
}

public class Ocp {
    public void run() {
        System.out.println("===== OCP: 개방-폐쇄 =====");

        DiscountCalculator discountCalculator = new DiscountCalculator();

        BasicDiscount basicDiscount = new BasicDiscount();
        GoldDiscount goldDiscount = new GoldDiscount();
        VipDiscount vipDiscount = new VipDiscount();

        System.out.println("일반 회원 -> " + discountCalculator.calc(basicDiscount, 10000));
        System.out.println("일반 회원 -> " + discountCalculator.calc(goldDiscount, 10000));
        System.out.println("일반 회원 -> " + discountCalculator.calc(vipDiscount, 10000));
        System.out.println();
    }
}
