public class J_array {

    public static void exam1() {
        int[] scores = new int[3];

        scores[0] = 10;
        scores[1] = 20;
        scores[2] = 30;

        System.out.println(scores);
        System.out.println(scores[0]);
        System.out.println(scores[1]);
        System.out.println(scores[2]);
    }

    public static void exam2() {
        int[] scores = {10, 20, 30};
        System.out.println(scores.length);

        for (int i = 0; i < scores.length; i++) {
            System.out.println(scores[i]);
        }
    }

    public static void exam3() {
        int[] scores = new int[3];

        for (int i = 1; i <= scores.length; i++) {
            scores[i - 1] = i * 10;
        }

        for (int s : scores) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        exam3();
    }
}
