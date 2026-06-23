package com.example.spring.springtheory.ch01.singleton_advanced;

public class GreetingServiceGood {
    private static final GreetingServiceGood instance = new GreetingServiceGood();

    private GreetingServiceGood() {}

    public static GreetingServiceGood getInstance() { return instance; }

    public String greet(String reqName) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {}
        return reqName;
    }
}
