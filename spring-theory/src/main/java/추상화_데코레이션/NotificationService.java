package 추상화_데코레이션;

// 이 클래스는 구체 클래스(Email/Sms/Kakao)를 절대 알면 안 된다. 오직 NotificationSender 인터페이스에만 의존한다.
public class NotificationService {

    private final NotificationSender sender;

    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String to, String message) {
        sender.send(to, message);
    }
}
