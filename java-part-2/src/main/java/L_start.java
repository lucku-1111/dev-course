import java.util.Scanner;

public class L_start {
    public static void main(String[] args) {
        L_vending_machine vendingMachine = new L_vending_machine();
        Scanner sc = new Scanner(System.in);
        int menuNumber = 0;
        int money = 0;


        while (true) {
            vendingMachine.printMenu();
            menuNumber = Integer.parseInt(sc.nextLine());
            if (menuNumber > 0 && menuNumber < 5) {
                vendingMachine.buy(menuNumber);
            } else if (menuNumber == 5) {
                System.out.println("돈을 넣으시오.");
                money = Integer.parseInt(sc.nextLine());
                vendingMachine.insertMoney(money);
            } else if (menuNumber == 6) {
                vendingMachine.returnMoney();
                break;
            } else {
                System.out.println("잘못된 입력값입니다.");
            }
        }
    }
}
