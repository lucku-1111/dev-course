package 과제.가계부;

import java.util.Scanner;

public class Start {
    private static AccountBookImpl book = new AccountBookImpl();

    private static int getChoice() {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    private static void printPrompt() {
        System.out.println("===== 가계부 =====\n" +
                "1. 내역 추가\n" +
                "2. 내역 조회\n" +
                "3. 전체 삭제\n" +
                "4. 내역 삭제\n" +
                "5. 종료\n" +
                "번호 입력 >");
    }

    public static void main(String[] args) {
        while (true) {
            printPrompt();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    book.addAccount();
                    break;
                case 2:
                    book.showAccout();
                    break;
                case 3:
                    book.deleteAll();
                    break;
                case 4:
                    book.deleteItem();
                    break;
                case 5:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력값입니다. 다시 입력해주세요.");
            }
        }
    }
}
