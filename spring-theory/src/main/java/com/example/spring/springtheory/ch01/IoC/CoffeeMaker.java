package com.example.spring.springtheory.ch01.IoC;

public class CoffeeMaker {
    private Bean bean;

    public CoffeeMaker(Bean bean) {
        this.bean = bean;
    }

    void brew() {
        System.out.println(bean.name() + "로 커피를 내립니다");
    }
}
