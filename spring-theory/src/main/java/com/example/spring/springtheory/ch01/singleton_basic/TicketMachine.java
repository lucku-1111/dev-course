package com.example.spring.springtheory.ch01.singleton_basic;

public class TicketMachine {
    private static final TicketMachine ticketMachine = new TicketMachine();
    private int num = 0;

    private TicketMachine() {}

    public static TicketMachine getInstance() {
        return ticketMachine;
    }

    public int issue() {
        return ++num;
    }
}
