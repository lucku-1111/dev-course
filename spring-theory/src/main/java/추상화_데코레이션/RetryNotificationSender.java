package 추상화_데코레이션;

public class RetryNotificationSender implements NotificationSender {
    private final NotificationSender delegate;

    public RetryNotificationSender(NotificationSender delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String to, String message) {
        int attempt = 3;

        while (attempt-- > 0) {
            try {
                delegate.send(to, message);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                if (attempt == 0) {
                    throw e;
                }
            }
        }
    }
}
