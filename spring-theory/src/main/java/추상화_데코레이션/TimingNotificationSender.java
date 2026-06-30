package 추상화_데코레이션;

public class TimingNotificationSender implements NotificationSender {
    private final NotificationSender sender;

    public TimingNotificationSender(NotificationSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(String to, String message) {
        long start = System.currentTimeMillis();
        sender.send(to, message);
        System.out.println(System.currentTimeMillis() - start + "ms 소요됨");
    }
}
