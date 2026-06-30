package com.example.spring.springtheory.ch05.ex_5_4.service;

// * 아무 것도 하지 않는 대역
// 메일 발송이 핵심이 아닌 상황
// 운영에서는 JavaMailSender 등으로 실제 발송되는 구현을 끼우면 된다.(코드 변경없이 설정만으로..)
public class DummyMailSender implements MailSender {
    @Override
    public void send(Mail mail) {

    }
}
