public class L_cidar extends L_drink {

    public L_cidar() {
        super("사이다", 500);
    }

    @Override
    public void dispense() {
        System.out.println("사이다가 나왔습니다.");
    }
}
