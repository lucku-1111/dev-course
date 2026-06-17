package 스레드_실행_제어_익히기;

import java.util.Scanner;

class PrintDash extends Thread {

    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("-");
        }
    }
}

class PrintBar extends Thread {

    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("|");
        }
    }
}

class SleepThread extends Thread {

    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("-");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("<<종료>>");
    }
}

class CountThread extends Thread {

    public void run() {
        int i = 10;
        while (i != 0 && !isInterrupted()) {
            System.out.println(i--);
            for (long x = 0; x < 2_500_000_000L; x++) ;
        }
        System.out.println("카운트가 종료되었습니다.");
    }
}

class CountSleepThread extends Thread {

    public void run() {
        int i = 10;
        while (i != 0 && !isInterrupted()) {
            System.out.println(i--);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("자다가 깨어남! (InInterruptedException)");
                break;
            }
        }
        System.out.println("카운트가 종료되었습니다.");
    }
}

class YieldThread extends Thread {

    private String name;

    public YieldThread(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + " 실행 중. 반복: " + i);
            Thread.yield();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class ManyPrintThread extends Thread {

    private char c;

    public ManyPrintThread(char c) {
        this.c = c;
    }

    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print(c);
        }
    }
}

public class Thread_control {

    public static void exam1() {
        new PrintDash().start();
        new PrintBar().start();
    }

    public static void exam2() {
        new SleepThread().start();
    }

    public static void exam3() {
        CountThread t1 = new CountThread();
        t1.start();
        new Scanner(System.in).nextLine();
        t1.interrupt();
    }

    public static void exam4() {
        CountSleepThread t2 = new CountSleepThread();
        t2.start();
        new Scanner(System.in).nextLine();
        t2.interrupt();
    }

    public static void exam5() {
        new YieldThread("스레드1").start();
        new YieldThread("스레드2").start();
    }

    public static void exam6() {
        ManyPrintThread t3 = new ManyPrintThread('-');
        ManyPrintThread t4 = new ManyPrintThread('|');
        t3.start();
        t4.start();

        long start = System.currentTimeMillis();
        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n소요시간: " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void main(String[] args) {
        exam6();
    }
}
