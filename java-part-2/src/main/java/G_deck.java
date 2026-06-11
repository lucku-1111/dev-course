class G_deck {
    final int CARD_NUM = 52;
    G_card[] cards = new G_card[CARD_NUM];

    public G_deck() {
        int i = 0;

        for (int k = G_card.KIND_MAX; k > 0; k--) {
            for (int n = 0; n < G_card.NUM_MAX; n++) {
                cards[i++] = new G_card(k, n + 1);
            }
        }
    }

    public G_card pick(int index) {
        return cards[index];
    }

    public G_card pick() {
        int index = (int) (Math.random() * CARD_NUM); // 덱에서 카드 하나를 선택한다.
        return pick(index);
    }

    public void shuffle() {
        for (int i = 0; i < cards.length; i++) {
            int index = (int) (Math.random() * CARD_NUM);

            G_card temp = cards[i];
            cards[i] = cards[index];
            cards[index] = temp;
        }
    }
}
