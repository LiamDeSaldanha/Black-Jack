public enum Card {

    Club_2(2, Suit.CLUBS),
    Club_3(3, Suit.CLUBS),
    Club_4(4, Suit.CLUBS),
    Club_5(5, Suit.CLUBS),
    Club_6(6, Suit.CLUBS),
    Club_7(7, Suit.CLUBS),
    Club_8(8, Suit.CLUBS),
    Club_9(9, Suit.CLUBS),
    Club_10(10, Suit.CLUBS),
    Club_JACK(10, Suit.CLUBS, true),
    Club_QUEEN(10, Suit.CLUBS, true),
    Club_KING(10, Suit.CLUBS, true),
    Club_ACE(1, Suit.CLUBS, true),

    Diamond_2(2, Suit.DIAMONDS),
    Diamond_3(3, Suit.DIAMONDS),
    Diamond_4(4, Suit.DIAMONDS),
    Diamond_5(5, Suit.DIAMONDS),
    Diamond_6(6, Suit.DIAMONDS),
    Diamond_7(7, Suit.DIAMONDS),
    Diamond_8(8, Suit.DIAMONDS),
    Diamond_9(9, Suit.DIAMONDS),
    Diamond_10(10, Suit.DIAMONDS),
    Diamond_JACK(10, Suit.DIAMONDS, true),
    Diamond_QUEEN(10, Suit.DIAMONDS, true),
    Diamond_KING(10, Suit.DIAMONDS, true),
    Diamond_ACE(1, Suit.DIAMONDS, true),

    Heart_2(2, Suit.HEARTS),
    Heart_3(3, Suit.HEARTS),
    Heart_4(4, Suit.HEARTS),
    Heart_5(5, Suit.HEARTS),
    Heart_6(6, Suit.HEARTS),
    Heart_7(7, Suit.HEARTS),
    Heart_8(8, Suit.HEARTS),
    Heart_9(9, Suit.HEARTS),
    Heart_10(10, Suit.HEARTS),
    Heart_JACK(10, Suit.HEARTS, true),
    Heart_QUEEN(10, Suit.HEARTS, true),
    Heart_KING(10, Suit.HEARTS, true),
    Heart_ACE(1, Suit.HEARTS, true),

    Spade_2(2, Suit.SPADES),
    Spade_3(3, Suit.SPADES),
    Spade_4(4, Suit.SPADES),
    Spade_5(5, Suit.SPADES),
    Spade_6(6, Suit.SPADES),
    Spade_7(7, Suit.SPADES),
    Spade_8(8, Suit.SPADES),
    Spade_9(9, Suit.SPADES),
    Spade_10(10, Suit.SPADES),
    Spade_JACK(10, Suit.SPADES, true),
    Spade_QUEEN(10, Suit.SPADES, true),
    Spade_KING(10, Suit.SPADES, true),
    Spade_ACE(1, Suit.SPADES, true);

    public enum Suit {
        SPADES("♠"), // Unicode for ♠
        HEARTS("♥"), // Unicode for ♥
        DIAMONDS("♦"), // Unicode for ♦
        CLUBS("♣"); // Unicode for ♣

        private final String symbol;

        // Constructor
        Suit(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private int rank;
    private Suit suit;
    private boolean courtCard;
    private String suitString;
    private String rankString;
    private boolean faceDown;
    /*
     * private final static int cardSymbolY = 2 * tile;
     * private final static int cardSymbolX = 2 * tile;
     * private final static int cardRankY = 2 * tile;
     * private final static int cardRankX = 2 * tile;
     */

    private Card(int rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        courtCard = false;
        this.suitString = suit.getSymbol();
        this.rankString = Integer.toString(rank);
    }

    private Card(int rank, Suit suit, boolean courtCard) {
        this.rank = rank;
        this.suit = suit;
        this.courtCard = courtCard;
        this.suitString = suit.getSymbol();
        this.rankString = Integer.toString(rank);
    }

    public int getRank() {
        return rank;
    }

    public void setFaceDown() {
        faceDown = true;
    }

    public void setFaceUp() {
        faceDown = false;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isCourtCard() {
        return courtCard;
    }

    public boolean equals(Card card) {
        if (card != null) {
            if (this.rank == card.getRank() && this.suit == card.getSuit() && this.courtCard == card.isCourtCard()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean equalsNoSuit(Card card) {
        if (card != null) {
            if (this.rank == card.getRank() && this.courtCard == card.isCourtCard()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isAce() {
        if (this.rank == 1 && this.isCourtCard()) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Card added: " + rank + " " + suit + " Face card: " + courtCard;
    }

    public String getCardSuit() {

        return suitString;
    }

    public String getCardRank() {
        return rankString;
    }
}
