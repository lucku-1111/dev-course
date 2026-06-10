package 텍스트_RPG_전투;

public class Main {
    public static void main(String[] args) {
        Character hero = new Character("용사", 100, 30);
        Character[] monsters = {
                new Character("슬라임"),              // 기본 생성자
                new Character("고블린", 50, 8),       // 커스텀
                new Character("드래곤", 120, 20)      // 보스
        };

        for (Character m : monsters) {
            System.out.println("\n=== 다음 상대: ===");
            m.showStatus();

            while (hero.isAlive() && m.isAlive()) {
                hero.attack(m);
                m.attack(hero);
                hero.showStatus();
                m.showStatus();
                if (!m.isAlive()) {
                    System.out.println(">> 몬스터를 쓰러뜨렸다!");
                    break;
                } else if (!hero.isAlive()) {
                    System.out.println("게임 오버...");
                    break;
                }
            }
        }
    }
}
