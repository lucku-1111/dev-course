package com.example.spring.springtheory.ch01.IoC;

public class CoffeeContainer {
    CoffeeMaker getCoffeeMaker() {
        return new CoffeeMaker(new ColombiaBean());
    }
}
