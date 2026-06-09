class Card {
    String kind;
    int number;
    static int width = 100;
    static int height = 150;
}


public class B_variable {
    public static void main(String[] args) {
        System.out.println(Card.width);
        System.out.println(Card.height);

        Card c1 = new Card();
        c1.kind = "Club";
        c1.number = 1;
        System.out.println(c1.kind + " " + c1.number);

        Card c2 = new Card();
        c2.kind = "Diamond";
        c2.number = 13;
        System.out.println(c2.kind + " " + c2.number);

        // 모두 같은 저장공간을 참조하므로 항상 같은 값을 갖게 된다.
        System.out.println(c1.width + " " + c1.height);
        System.out.println(c2.width + " " + c2.height);
    }
}