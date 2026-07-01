package AOP.service;

public class OrderServiceImpl implements OrderService {
    @Override
    public String placeOrder(String item) {
        sleep(80L);
        return "주문완료: " + item;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
