package AOP.service;

public class MemberServiceImpl implements MemberService {
    @Override
    public String register(String id) {
        sleep(50L);
        return "가입완료: " + id;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
