public class F_operator {
    // [산술 연산자]
    public static void operExam1() {
        int a = 10;
        int b = 4;

        System.out.printf("%d + %d = %d\n", a, b, a + b);
        System.out.printf("%d - %d = %d\n", a, b, a - b);
        System.out.printf("%d * %d = %d\n", a, b, a * b);
        System.out.printf("%d / %d = %d\n", a, b, a / b);
        // 단일 %는 포맷 지정자로 인식이 안되기 때문에 출력하기 위해선 %%를 사용해야 한다.
        System.out.printf("%d %% %d = %f\n", a, b, (float)a % b);
    }

    // [단항 연산자]
    public static void operExam2() {
        // 증감 연산자 : 피연산자의 값을 1 증가시킨다.
        int i = 5;
        System.out.println("========= 증감 =========");
        System.out.println("전위형 : " + ++i); // 5 + 1
        System.out.println(i); // 6
        System.out.println("후위형 : " + i++); // 6 -> 6 + 1
        System.out.println(i); // 7
        i = i + 1;
        i += 1;
        System.out.println("i = " + i);

        // 감소 연산자 : 피연산자의 값을 1 감소시킨다
        i = 5;
        System.out.println("========= 감소 =========");
        System.out.println("전위형 : " + --i);
        System.out.println(i);
        System.out.println("후위형 : " + 4);
        System.out.println(i);
        i = i - 1;
        i -= 1;
        System.out.println("i = " + i);

    }

    // [비교 연산자]
    public static void operExam3() {
        System.out.printf("10 == 10.0f \t %b\n", 10 == 10.0f );
        System.out.printf("'0' == 0 \t %b\n", '0' == 0);
        System.out.printf("'A' == 65 \t %b\n", 'A' == 65 );
        System.out.printf("'A' > 'B' \t %b\n", 'A' > 'B');
        System.out.printf("'A' + 1 != 'B' \t %b\n", 'A' + 1 != 'B');
    }

    public static void operExam4() {
        String str1 = new String("Hello");
        String str2 = new String("Hello");
        System.out.println(str1 == str2);

        String str3 = "Hello";
        String str4 = "Hello";
        System.out.println(str3 == str4);

        // 두 문자열을 비교할 때는 비교 연산자 '=='대신 equals()라는 메서드를 이용해야한다.
        System.out.println(str1.equals(str2));
    }

    public static void operExam5() {
        boolean a = true;
        boolean b = false;
        boolean c = true;

        System.out.println("a && b : " + (a && b));
        System.out.println("c && a : " + (c && a));
        System.out.println("a || b : " + (a || b));
        System.out.println("!a : " + !a);
    }

    public static void main(String[] args) {
        operExam1();
        operExam2();
        operExam3();
        operExam4();
        operExam5();
    }
}
