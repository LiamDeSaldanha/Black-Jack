public class Deck {
    private Card[] cards = new Card[52];

    public Deck() {
        int index = 0;
        for (Suit suit : Suit.values()) {
            for (int k = 1; k < 14; k++) {

                if (k > 10) {
                    cards[index] = new Card(10, suit, true);
                } else {
                    cards[index] = new Card(k, suit);
                }
                index++;
            }
        }
    }

    public Card[] getCards() {
        return cards;
    }
}
