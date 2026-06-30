package 추상화_데코레이션;

public class KakaoNotificationSender implements NotificationSender {
    @Override
    public void send(String to, String message) {
        System.out.printf("[KAKAO] to=%s : %s%n", to, message);
    }
}
