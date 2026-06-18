package wait_notify;

public class Chat {

    private boolean flag = false;

    public synchronized void question(String msg) {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("질문: " + msg);
        flag = true;
        notify();
    }

    public synchronized void answer(String msg) {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("답변: " + msg);
        flag = false;
        notify();
    }
}
