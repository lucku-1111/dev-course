public class B_variable_method {
    public static void jaRyoHyeong() {
        byte myByte = 127;
        System.out.println("byte: " + myByte);

        short myShort = 30200;
        System.out.println("short: " + myShort);

        int integer = 1000000;
        System.out.println("int: " + integer);

        long myLong = 1234567890123456789L;
        System.out.println("long: " + myLong);

        char myChar = 'a';
        System.out.println("myChar:" + myChar);
        myChar = 70;
        System.out.println("myChar:" + myChar);

        boolean complete = true;
        System.out.println("complete: " + complete);

        final float PI = 3.14f;
        System.out.println("PI: " + PI);
        final double LONG_PI = 3.1415926535;
        System.out.println("LONG_PI: " + LONG_PI);

        String str = "Hello World!";
        System.out.println("str: " + str);
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static int minus(int a, int b) {
        return a - b;
    }

    public static void printResult(int result) {
        System.out.println("result: " + result);
    }

    public static void main(String[] args) {
        jaRyoHyeong();
        printResult(add(20, 10));
        printResult(minus(20, 10));
    }
}
