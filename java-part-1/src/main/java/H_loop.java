import java.util.Scanner;

public class H_loop {

    public static void exam1() {
        for (int i = 0; i < 10; i++) {
            System.out.println("cnt: " + i);
        }
    }

    public static void exam2() {
        for (int i = 1; i < 10; i++) {
            System.out.println("2 * " + i + " = " + (2 + i));
        }
    }

    public static void exam3() {
        for (int i = 1; i < 3; i++) {
            System.out.println("i = " + i);
            for (int j = 1; j < 5; j++) {
                System.out.println("j = " + j);
            }
        }
    }

    public static void practice1() {
        System.out.println("=== 구구단 전체 ===");
        for (int dan = 2; dan < 10; dan++) {
            System.out.println("=== " + dan + "단 ===");
            for (int k = 1; k < 10; k++) {
                System.out.println(dan + " * " + k + " = " + (dan * k));
            }
        }
    }

    public static void exam4() {
        for ( int i = 1; i <= 100; i++ ) {
            if ( i % 2 != 1 ) {
                System.out.println(i);
            }
        }
    }

    public static void practice2() {
        for ( int i = 1; i <= 100; i++ ) {
            if ( i % 2 != 0 ) {
                System.out.println(i);
            }
        }
    }

    public static void exam5() {
        for (int num = 1; num <= 100; num++) {
            if (num == 30) {
                break;
            }
            System.out.println(num);
        }
        System.out.println("탈출!");
    }

    public static void exam6() {
        for (int i = 0; i < 3; i++) {
            System.out.println("첫 번째 루프: " + i);
            for (int j = 0; j < 3; j++) {
                System.out.println("두 번째 루프: " + j);
                for (int k = 0; k < 3; k++) {
                    System.out.println("세 번째 루프: " + k);
                }
            }
        }
    }

    public static void exam7() {
        int i = 0;
        for (; i < 3;) {
            System.out.println(i++);
        }
    }

    public static void exam8() {
        int i = 0;
        for (;;) {
            if ( i > 10 ) break;
            System.out.println(++i);
        }
    }

    public static void exam9() {
        int i = 0;
        for (;true;) {
            System.out.println(i++);
        }
    }

    public static void exam10() {
        for (int i = 9; i >= 0; i--) {
            System.out.println(i);
        }
    }

    public static void practice3() {

        for (int dan = 9; dan > 1; dan-- ) {
            System.out.println("dan = " + dan);
            for (int i = 9; i > 0; i--) {
                System.out.println(dan + " * " + i + " = " + (dan * i));
            }
        }
    }

    public static void exam11() {
        int cnt = 0;
        while (cnt <= 10) {
            System.out.println("cnt = " + cnt);
            cnt++;
        }
    }

    public static void practice4() {
        System.out.println("구구단 2단 출력");
        int i = 1;
        while (i < 10) {
            System.out.println(" 2 * " + i + " = " + (2 * i));
            i++;
        }
    }

    public static void practice5() {
        int dan = 2;
        while (dan < 10) {
            System.out.println("[" + dan + "단]");
            int i = 1;
            while (i < 10) {
                System.out.println(dan + " * " + i + " = " + (dan * i));
                i++;
            }
            dan++;
        }
    }

    public static void exam12() {
        int i = 3;
        while (true) {
            if (i == 0) break;
            System.out.println(i--);
        }
    }

    public static void exam13() {
        int i = 10;
        while (--i > 0) {
            System.out.println(i);
        }

        System.out.println("=========");
        i = 10;
        while ( i-- > 0 ) {
            System.out.println(i);
        }
    }

    public static void practice6() {
        Scanner sc = new Scanner(System.in);

        while ( true ) {
            System.out.println("합을 구할 정수를 입력해주세요.(만약 종료를 원하면 0을 누르시요.)");
            int num = sc.nextInt();

            if ( num == 0 ) break;

            int sum = 0;
            for (int i = 1; i <= num; i++) {
                sum += i;
            }
            System.out.println("결과 : " + sum);
        }
        System.out.println("감사합니다.");
    }

    public static void practice7() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("원하는 구구단의 단을 입력하시오.(0을 누르면 종료됩니다)");
            int dan = sc.nextInt();

            if (dan == 0) break;

            for (int i = 1; i <= 9; i++) {
                System.out.println(dan + " * " + i + " = " + (dan * i));
            }
        }
    }

    public static void exam14() {
        int i = 1;
        while (i <= 10) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
            i++;
        }
    }

    public static void exam15() {
        int i = 0;

        while (i != 0) {
            System.out.println("while문입니다.");
        }

        do {
            System.out.println("do-while문입니다.");
        } while ( i != 0 );
    }

    public static void main(String[] args) {
        exam15();
    }
}
