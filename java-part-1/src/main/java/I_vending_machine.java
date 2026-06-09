import java.util.Scanner;

public class I_vending_machine {
    static final int COKE = 500, CIDER = 500, FANTA = 300, WATER = 200;

    public static void printMenu(int totalMoney) {
        System.out.println("============== 자판기 ==============");
        System.out.println("[1]콜라 : 500, [2]사이다 : 500, [3]환타 : 300, [4]물 : 200, [5]돈 넣기, [6]종료");
        System.out.println("현재 금액 : " + totalMoney);
        System.out.println("====================================");
    }

    public static int getChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.println("원하는 메뉴를 선택하시오.");

        return sc.nextInt();
    }

    public static int getMoney() {
        Scanner sc = new Scanner(System.in);
        System.out.println("돈을 넣으시오.");

        return sc.nextInt();
    }

    public static int calcMoney(int money, int price) {
        return money - price;
    }

    public static void calcMoneyException() {
        System.out.println("잔돈이 부족합니다.");
    }

    public static void main(String[] args) {
        int totalMoney = 0;

        while (true) {
            printMenu(totalMoney);
            int choice = getChoice();
            int result = -1;
            switch (choice) {
                case 1:
                    result = calcMoney(totalMoney, COKE);
                    if (result < 0) {
                        calcMoneyException();
                    } else {
                        totalMoney = result;
                        System.out.println("콜라가 나왔습니다.");
                    }
                    break;
                case 2:
                    result = calcMoney(totalMoney, CIDER);
                    if (result < 0) {
                        calcMoneyException();
                    } else {
                        totalMoney = result;
                        System.out.println("사이다가 나왔습니다.");
                    }
                    break;
                case 3:
                    result = calcMoney(totalMoney, FANTA);
                    if (result < 0) {
                        calcMoneyException();
                    } else {
                        totalMoney = result;
                        System.out.println("환타가 나왔습니다.");
                    }
                    break;
                case 4:
                    result = calcMoney(totalMoney, WATER);
                    if (result < 0) {
                        calcMoneyException();
                    } else {
                        totalMoney = result;
                        System.out.println("물이 나왔습니다.");
                    }
                    break;
                case 5:
                    totalMoney += getMoney();
                    break;
                case 6:
                    System.out.println("잔돈 " + totalMoney + "원이 반환되었습니다.");
                    return;
                default:
                    System.out.println("잘못된 입력값입니다. 다시 입력해주세요.");
                    break;
            }
        }
    }
}
