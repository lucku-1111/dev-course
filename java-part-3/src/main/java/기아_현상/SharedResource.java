package 기아_현상;

public class SharedResource {

    private boolean isAvailable = false;

    public synchronized void waitForResource(String threadName) {
        while (!isAvailable) {
            try {
                System.out.println(threadName + " is waiting for resource...");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(threadName + " got the resource!");
        isAvailable = false;
    }

    public synchronized void makeResourceAvailable() {
        isAvailable = true;
        System.out.println("Resource is now available!");
        notifyAll();
    }
}
