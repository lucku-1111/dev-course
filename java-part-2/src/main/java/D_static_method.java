class MathOperation {

    // 정적변수
    static final double PI = 3.14159;

    // 정적메서드
    static double add(double a, double b) {
        return a + b;
    }

    // 정적메서드
    static double calculateArea(double radius) {
        return PI * radius * radius;
    }

}

public class D_static_method {
    static void main(String[] args) {
        double added = MathOperation.add(10, 20);
        double v = MathOperation.calculateArea(5);

        System.out.println(added);
        System.out.println(v);
    }
}
