public class J_array_2 {

    public static void exam1() {
        int[][] scores = new int[3][2];
        scores[0][0] = 10;
        scores[0][1] = 20;
        scores[1][0] = 30;
        scores[1][1] = 40;
        scores[2][0] = 50;
        scores[2][1] = 60;

        System.out.println(scores.length);
        System.out.println(scores[0].length);

        for (int i = 0; i < scores.length; i++) {
            for (int j = 0; j < scores[i].length; j++) {
                System.out.println(scores[i][j]);
            }
        }
    }

    public static void exam2() {
        int[][] scores = {{1, 2}, {3, 4}};

        for (int i = 0; i < scores.length; i++) {
            for (int j = 0; j < scores[i].length; j++) {
                System.out.println(scores[i][j]);
            }
        }
    }

    public static void exam3() {
        int[][] scores = new int[2][];

        scores[0] = new int[] {1, 2};
        scores[1] = new int[3];

        scores[1][0] = 3;
        scores[1][1] = 4;
        scores[1][2] = 5;

        for (int i = 0; i < scores.length; i++) {
            for (int j = 0; j < scores[i].length; j++) {
                System.out.println(scores[i][j]);
            }
        }
    }

    public static void exam4() {
        int[][][] scores = new int[2][2][2];
        int[][][] scores2 = new int[2][2][];
    }


    public static void main(String[] args) {
        exam3();
    }
}
