package com.example.spring.springtheory.ch01.singleton_advanced;

public class Main {
    static int badMismatch = 0;
    static int goodMismatch = 0;

    static void main() throws InterruptedException {
        int N = 30;

        Thread[] t1 = new Thread[N];
        for (int i = 0; i < N; i++) {
            final String myName = "손님" + i;
            t1[i] = new Thread(() -> {
                String r = GreetingServiceBad.getInstance().greet(myName);
                if (!r.equals(myName)) {
                    synchronized (Main.class) { badMismatch++; }
                }
            });
        }
        for (Thread t : t1) t.start();
        for (Thread t : t1) t.join();

        System.out.println("[필드에 저장] 엉킴: " + badMismatch + " / " + N);
        System.out.println("[파라미터로]  엉킴: " + goodMismatch + " / " + N);
    }
}
