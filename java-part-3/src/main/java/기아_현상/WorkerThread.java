package 기아_현상;

public class WorkerThread extends Thread {

    private SharedResource resource;
    private String name;

    public WorkerThread(SharedResource resource, String name) {
        this.resource = resource;
        this.name = name;
    }

    public void run() {
        while (true) {
            resource.waitForResource(name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
