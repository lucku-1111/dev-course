package com.example.spring.springtheory.ch01.singleton_basic;

public class NaiveTicketMachine {
    private int num = 0;

    public int issue() {
        return ++num;
    }
}
