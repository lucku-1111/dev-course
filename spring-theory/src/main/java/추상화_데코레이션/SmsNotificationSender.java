package 추상화_데코레이션;

public class SmsNotificationSender implements NotificationSender {
    @Override
    public void send(String to, String message) {
        System.out.printf("[SMS] to=%s : %s%n", to, message);
    }
}
