package 세마포어_게임;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon(2);
        List<String> names = new ArrayList<>();

        names.add("전사");
        names.add("마법사");
        names.add("궁수");
        names.add("도적");
        names.add("성기사");

        for (String name : names) {
            new Adventurer(dungeon, name).start();
        }
    }
}
