import java.util.Scanner;

public class I_vending_machine {

    static final int COKE = 500, CIDER = 500, FANTA = 300, WATER = 200;

    private int totalMoney = 0; // 상태를 객체가 직접 관리
    private Scanner sc = new Scanner(System.in);

    public void printMenu() { // totalMoney 파라미터 불필요
        System.out.println("============== 자판기 ==============");
        System.out.println("[1]콜라 : 500, [2]사이다 : 500, [3]환타 : 300, [4]물 : 200, [5]돈 넣기, [6]종료");
        System.out.println("현재 금액 : " + totalMoney);
        System.out.println("====================================");
    }

    public int getChoice() {
        System.out.println("원하는 메뉴를 선택하시오.");
        return sc.nextInt();
    }

    public void insertMoney() {
        System.out.println("돈을 넣으시오.");
        totalMoney += sc.nextInt(); // 직접 상태 변경
    }

    public void purchase(int price, String itemName) {
        int result = totalMoney - price;
        if (result < 0) {
            System.out.println("잔돈이 부족합니다.");
        } else {
            totalMoney = result;
            System.out.println(itemName + "가 나왔습니다.");
        }
    }

    public void run() {
        while (true) {
            printMenu();
            int choice = getChoice();
            switch (choice) {
                case 1: purchase(COKE,  "콜라");  break;
                case 2: purchase(CIDER, "사이다"); break;
                case 3: purchase(FANTA, "환타");  break;
                case 4: purchase(WATER, "물");    break;
                case 5: insertMoney(); break;
                case 6:
                    System.out.println("잔돈 " + totalMoney + "원이 반환되었습니다.");
                    return;
                default:
                    System.out.println("잘못된 입력값입니다. 다시 입력해주세요.");
            }
        }
    }

    public static void main(String[] args) {
        new I_vending_machine().run(); // 객체 생성 후 실행
    }
}