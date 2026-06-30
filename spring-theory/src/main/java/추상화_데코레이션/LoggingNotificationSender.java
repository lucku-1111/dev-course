package 추상화_데코레이션;

public class LoggingNotificationSender implements NotificationSender {
    private final NotificationSender delegate;

    public LoggingNotificationSender(NotificationSender delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String to, String message) {
        System.out.println("[발송 시작] to:" + to);
        delegate.send(to, message);
        System.out.println("[발송 완료] to:" + to);
    }
}
