import java.util.Scanner;

public class D_scanner_print {
    public static void main(String[] args) {
        String name = "이석우";
        int age = 29;

        System.out.printf("이름: %s\n", name);
        System.out.printf("나이: %d\n", age);

        System.out.println("당신의 이름은?");
        Scanner sc = new Scanner(System.in);
        String userName = sc.nextLine();

        System.out.println("당신의 나이는?");
        int userAge = sc.nextInt();

        System.out.printf("당신의 이름은 %s이며, 나이는 %d살입니다.", userName, userAge);
    }
}
