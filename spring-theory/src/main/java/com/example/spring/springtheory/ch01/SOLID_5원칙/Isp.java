package com.example.spring.springtheory.ch01.SOLID_5원칙;

interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

interface Faxer {
    void fax();
}

class SimplePrinter implements Printer {
    @Override
    public void print() {
        System.out.println("구형 프린터: 인쇄만 합니다");
    }
}

class SmartMachine implements Printer, Scanner {
    @Override
    public void print() {
        System.out.println("복합기: 인쇄");
    }

    @Override
    public void scan() {
        System.out.println("복합기: 스캔");
    }
}

public class Isp {
    public void run() {
        System.out.println("===== ISP: 인터페이스 분리 =====");

        SimplePrinter simplePrinter = new SimplePrinter();
        SmartMachine smartMachine = new SmartMachine();

        simplePrinter.print();
        smartMachine.print();
        smartMachine.scan();
        System.out.println();
    }
}
