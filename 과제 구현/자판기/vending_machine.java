package 과제.자판기;

import java.util.Scanner;

public class vending_machine {
    static final int[] DRINK = {500, 700, 300, 200};
    static int money = 0;

    public static void run() {
        printMenu(money);

        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        if (option < 1 || option > 6) {
            System.out.println("선택지에 존재하지 않는 값입니다.");
            return;
        }
        if (option == 6) {
            return;
        } else if (option == 5) {
            System.out.println("얼마를 넣으시겠습니까?");
            money += sc.nextInt();
        } else {
            if (money < DRINK[option - 1]) {
                System.out.println("돈이 부족합니다.");
            } else {
                printDrink(option);
            }
        }
        run();
    }

    public static void printDrink(int option) {
        switch (option) {
            case 1:
                System.out.println("콜라가 결제되었습니다.");
                money -= DRINK[0];
                break;
            case 2:
                System.out.println("사이다가 결제되었습니다.");
                money -= DRINK[1];
                break;
            case 3:
                System.out.println("환타가 결제되었습니다.");
                money -= DRINK[2];
                break;
            case 4:
                System.out.println("물이 결제되었습니다.");
                money -= DRINK[3];
                break;
        }
    }

    public static void printMenu(int totalMoney) {
        System.out.println("================================= 자판기 ================================");
        System.out.println("[1]콜라-500원 [2]사이다-700원 [3]환타-300원 [4]물-200원 [5]돈넣기 [6]종료");
        System.out.println("현재 금액 : " + totalMoney + "원");
        System.out.println("==========================================================================");
    }

    public static void main(String[] args) {
        run();
    }
}
