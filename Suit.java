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
