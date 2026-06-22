package com.example.spring.springtheory.ch01.SOLID_5원칙;

class Bird {
    void eat() {
        System.out.println("냠냠 먹습니다.");
    }
}

class FlyingBird extends Bird {
    void fly() {
        System.out.println("훨훨 납니다.");
    }
}

class Penguin extends Bird {
    void swim() {
        System.out.println("첨벙 헤엄칩니다.");
    }
}

public class Lsp {
    public void run() {
        System.out.println("===== LSP: 리스코프 치환 =====");

        Bird flyingBird = new FlyingBird();
        Bird penguin = new Penguin();

        flyingBird.eat();
        penguin.eat();
        ((FlyingBird) flyingBird).fly();
        ((Penguin) penguin).swim();
        System.out.println();
    }
}
