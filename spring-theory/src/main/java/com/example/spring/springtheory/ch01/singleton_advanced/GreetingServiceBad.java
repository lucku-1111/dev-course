package com.example.spring.springtheory.ch01.singleton_advanced;

public class GreetingServiceBad {
    private static final GreetingServiceBad instance = new GreetingServiceBad();

    private GreetingServiceBad() {}

    public static GreetingServiceBad getInstance() { return instance; }

    private String name;

    public String greet(String reqName) {
        this.name = reqName;
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {}
        return this.name;
    }

}
