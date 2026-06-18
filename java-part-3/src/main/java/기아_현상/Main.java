package 기아_현상;

import 회원관리_프로그램_최종.PricePlan;

public class Main {

    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        new WorkerThread(resource, "thread1").start();
        new WorkerThread(resource, "thread2").start();
        new WorkerThread(resource, "thread3").start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    resource.makeResourceAvailable();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
