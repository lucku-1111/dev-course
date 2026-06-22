package com.example.spring.springtheory.ch01.SOLID_5원칙;

class NotificationService {
    private MessageSender sender;

    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    void notifyUser(String msg) { sender.send(msg); }
}

interface MessageSender {
    void send(String msg);
}

class EmailSender implements MessageSender {
    @Override
    public void send(String msg) {
        System.out.println("이메일: " + msg);
    }
}

class SmsSender implements MessageSender {
    @Override
    public void send(String msg) {
        System.out.println("SMS: " + msg);
    }
}

public class Dip {
    public void run() {
        System.out.println("===== DIP: 의존관계 역전 =====");

        EmailSender emailSender = new EmailSender();
        SmsSender smsSender = new SmsSender();

        emailSender.send("주문이 완료되었습니다.");
        smsSender.send("주문이 완료되었습니다.");
    }
}
