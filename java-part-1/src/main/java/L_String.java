public class L_String {

    public static void exam1() {
        String str = "hello";

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            System.out.println(c);
        }
    }

    public static void exam2() {
        String str = "algorithm";
        String sub = str.substring(0, 5); // beginIndex는 포함, endIndex는 미포함
        System.out.println(sub);
    }

    public static void exam3() {
        String str = "one,two,three";
        String[] packets = str.split(",");

        for (String p : packets) {
            System.out.println(p);
        }
    }


    public static void main(String[] args) {
        exam3();
    }
}
