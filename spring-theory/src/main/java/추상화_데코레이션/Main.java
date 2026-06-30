package 추상화_데코레이션;

public class Main {
    static void main() {
        // Part A
        System.out.println("====Part A 실행====");
        NotificationService service1 = new NotificationService(new EmailNotificationSender());
        NotificationService service2 = new NotificationService(new SmsNotificationSender());
        NotificationService service3 = new NotificationService(new KakaoNotificationSender());

        service1.notifyUser("석우", "안녕하세요");
        service2.notifyUser("석우", "안녕하세요");
        service3.notifyUser("석우", "안녕하세요");

        // Part C-1
        System.out.println("\n====Part C-1 실행====");
        NotificationSender sender1 =
                new TimingNotificationSender(
                        new LoggingNotificationSender(
                                new RetryNotificationSender(
                                        new FlakyEmailSender())));

        new NotificationService(sender1).notifyUser("user@test.com", "안녕하세요");

        // Part C-2
        System.out.println("\n====Part C-2 실행====");
        NotificationSender sender2 =
                new TimingNotificationSender(
                        new RetryNotificationSender(
                                new LoggingNotificationSender(
                                        new FlakyEmailSender())));

        new NotificationService(sender2).notifyUser("user@test.com", "안녕하세요");
    }
}
