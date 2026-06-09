public class E_casting {
    public static void main(String[] args) {
        double d = 3.14;
        System.out.println(d);
        System.out.println((int) d);

        int n = 65;
        System.out.println(n);
        System.out.println((char) n);

        char c2 = 'A';
        System.out.println(c2);
        System.out.println((int) c2);

        float f = 3.14f;
        System.out.println(f);
        System.out.println((int) f);

        int n2 = 3;
        System.out.println(n2);
        System.out.println((float) n2);

        // 암시적 형변환
        byte b = 10;
        int i = b;

        char cc = 'A';
        int j = cc;

        int k = 100;
        double dd = k;

        // 명시적 형변환
        int i2 = 100;
        byte b2 = (byte) i2;

        double ddd = 9.78;
        int j2 = (int) ddd;
    }
}
