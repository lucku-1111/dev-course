public class M_vending_machine {

    private int totalMoney;
    private M_drink[] drinks;

    public M_vending_machine() {
        totalMoney = 0;
        drinks = new M_drink[] {
                new M_coke(),
                new M_cidar(),
                new M_fanta(),
                new M_water()
        };
    }

    // 돈 넣기
    public void insertMoney(int money) {
        totalMoney += money;
    }

    // 음료구매
    public void buy(int menuNumber) {
        M_drink drink = drinks[menuNumber - 1];
        if (drink.getPrice() > totalMoney) {
            System.out.println("잔액이 부족합니다.");
            return;
        }
        drink.dispense();
        totalMoney -= drink.getPrice();
    }

    // 종료 시 잔돈 반환
    public int returnMoney() {
        System.out.println("잔돈 "+ totalMoney + "원이 반환되었습니다.");
        return totalMoney;
    }

    public void printMenu() { // totalMoney 파라미터 불필요
        System.out.println("============== 자판기 ==============");
        System.out.println("[1]콜라 : 500, [2]사이다 : 500, [3]환타 : 700, [4]물 : 200, [5]돈 넣기, [6]종료");
        System.out.println("현재 금액 : " + totalMoney);
        System.out.println("====================================");
    }
}
