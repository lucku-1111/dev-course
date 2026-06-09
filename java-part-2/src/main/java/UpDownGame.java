import java.util.Random;
import java.util.Scanner;

public class UpDownGame {
    public static void printPrompt() {
        System.out.println("숫자를 맞혀보세요! (1 ~ 100)");
    }

    public static int getInput() {
        Scanner sc = new Scanner(System.in);

        System.out.printf("입력 > ");
        return sc.nextInt();
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int answer = rand.nextInt(100) + 1;
        int guess = 0;
        int count = 0;

        printPrompt();

        while (true) {
            guess = getInput();
            count++;
            if (guess < answer) {
                System.out.println("UP! 더 큰 수입니다.");
            } else if (guess > answer) {
                System.out.println("DOWN! 더 작은 수입니다.");
            } else {
                System.out.printf("정답입니다! %d번 만에 맞혔어요.\n", count);
                return;
            }
        }
    }
}
