package com.example.spring.springtheory.ch01.IoC;

interface ClickListener {
    void onClick();
}

class Button {
    private ClickListener listener;

    void setListener(ClickListener l) {
        this.listener = l;
    }

    void press() {
        System.out.println("[시스템] 버튼이 눌렸습니다");
        listener.onClick();
    }
}

class LikeAction implements ClickListener {
    @Override
    public void onClick() {
        System.out.println("내 코드 실행: 좋아요!");
    }
}

public class Hollywood {
}
