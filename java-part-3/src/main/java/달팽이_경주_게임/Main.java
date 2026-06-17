package 달팽이_경주_게임;

public class Main {

    public static void main(String[] args) {
        Race race = new Race();
        new Snail("김달팽", race).start();
        new Snail("최달팽", race).start();
        new Snail("박달팽", race).start();
    }
}
