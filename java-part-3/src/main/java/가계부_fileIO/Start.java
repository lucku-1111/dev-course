package 가계부_fileIO;

import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountBook book = new AccountBookImpl(sc);

        while (true) {
            System.out.println("===== 가계부 (File) =====\n" +
                    "1. 내역 추가\n" +
                    "2. 내역 조회\n" +
                    "3. 삭제\n" +
                    "4. 종료\n" +
                    "번호 입력 >");
            int menu;

            try {
                menu = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 번호입니다.");
                continue;
            }

            switch (menu) {
                case 1:
                    book.addAccount();
                    break;
                case 2:
                    book.showAccount();
                    break;
                case 3:
                    book.deleteAccount();
                    break;
                case 4:
                    System.out.println("종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다.");
            }
        }
    }
}
