class Data {
    int x = 5;
}

public class C_jvm_memory {
    static void main(String[] args) {
        exam4();
    }

    public static void exam1() {
        firstMethod();
    }

    public static void firstMethod() {
        secondMethod();
    }

    public static void secondMethod() {
        System.out.println("Second Method");
    }

    public static void exam2() {
        Data d = new Data();
        System.out.println(d.x);
        changeData(d);
        System.out.println(d.x);
    }

    public static void changeData(Data d) { // * 참조형 매개변수 : 변수의 값을 읽고 변경할 수 있다.
        d.x = 10;
    }

    public static void exam3() {
        // 얕은복사
        Data d1 = new Data();
        Data d2 = d1; // &123
        // 깊은복사
        Data d3 = copy(d1);
        d1.x = 10;
        System.out.println(d1.x);
        System.out.println(d2.x);
        System.out.println(d3.x);
    }

    public static Data copy(Data d) {
        Data temp = new Data();
        temp.x = d.x;
        return temp;
    }

    static void exam4() {
        int result = factorial(5);
        System.out.println(result);
    }

    static int factorial(int n) {
        // 탈출조건
        if (n == 1) {
            return 1;
        }

        return n * factorial(n - 1);
    }
}