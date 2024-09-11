public class Hand {
    // Define instance variables here
    private int playerID;
    private int ID;
    private Card[] cards;
    private boolean busted;
    private boolean doubled;
    private boolean firstHandAce;
    private int currentFreeCardPosition;
    private int runningTotal;
    private boolean blackJack;
    private boolean splittable;
    private boolean doublable;
    private boolean standing;
    private int bet;

    // Constructor
    public Hand(int playerID, int ID, int bet) {
        // Initialize instance variables here
        this.playerID = playerID;
        this.ID = ID;
        cards = new Card[22];
        busted = false;
        doubled = false;
        firstHandAce = false;
        blackJack = false;
        splittable = false;
        doublable = false;
        standing = false;
        currentFreeCardPosition = 0;
        runningTotal = 0;

        this.bet = bet;

    }

    public Hand() {
        // Initialize instance variables here

        cards = new Card[22];
        busted = false;
        doubled = false;
        firstHandAce = false;
        blackJack = false;
        splittable = false;
        doublable = false;
        standing = false;
        currentFreeCardPosition = 0;
        runningTotal = 0;

    }

    // Add a card to the hand
    public Card addCard() {
        // Implement the logic to add a card to the hand here
        Card card = BjSimulation.getShoe().getCard();
        if (currentFreeCardPosition <= 22) {

            if (doubled) {
                System.out.println("Cannot add another card");
                return null;

            }

            if (busted) {
                System.out.println("Cannot add another card");
                return null;

            }

            cards[currentFreeCardPosition] = card;
            runningTotal += card.getRank();

            if (currentFreeCardPosition == 0 && card.isAce()) {
                firstHandAce = true;
                System.out.println("first card is a ace");
            }
            if (currentFreeCardPosition == 1 && cards[0].equalsNoSuit(cards[1])) {
                splittable = true;
                System.out.println("Splittable cards");
            } else {
                splittable = false;
            }
            if (runningTotal == 21 && currentFreeCardPosition == 1) {
                blackJack = true;
                System.out.println("BlackJack");
            } else if (runningTotal > 21) {
                busted = true;
                System.out.println("Busted");
            }

            if (currentFreeCardPosition == 1) {
                doublable = true;
            } else if (currentFreeCardPosition == 0 && cards[0].isAce()) {
                doublable = true;
            } else {
                doublable = false;
            }

            currentFreeCardPosition++;
            return card;

        } else {
            System.out.println("Error card count is above 22. Auto Bust.");
            return null;
        }

    }

    public Card Double() {
        bet += bet;

        doubled = true;
        return addCard();

    }

    // Remove a card from the hand
    public Card removeCard(Card card) {
        // Implement the logic to remove a card from the hand here
        if (card == null) {
            Card temp = cards[currentFreeCardPosition - 1];
            cards[currentFreeCardPosition - 1] = null;
            return temp;
        } else {
            return null;
        }

    }

    public Card Split() {

        if (currentFreeCardPosition == 2) {
            currentFreeCardPosition--;
            return removeCard(cards[2]);
        } else {
            return null;
        }

    }

    public void Stand() {
        standing = true;
    }

    public boolean busted() {
        return busted;
    }

    public boolean firstCardIsAce() {
        return firstHandAce;
    }

    public boolean isBlackJack() {
        return blackJack;
    }

    public int getHandValue() {
        return runningTotal;
    }

    public boolean isStanding() {
        return standing;
    }

    public int getNextFreePosition() {
        return currentFreeCardPosition;
    }

    public boolean isDoubled() {
        return doubled;
    }

    public String toString() {
        String temp = "";
        for (Card card : cards) {
            if (card != null)
                temp += card + "\n";
        }
        return playerID + "\nHand: " + ID + "\n" + temp + "Total: " + runningTotal + "\n";
    }

    public void newHand() {
        for (Card card : cards) {
            card = null;
        }
        busted = false;
        doubled = false;
        firstHandAce = false;
        blackJack = false;
        splittable = false;
        doublable = false;
        standing = false;
        currentFreeCardPosition = 0;
        runningTotal = 0;
        bet = 0;
    }

    public Card getCard(int pos) {
        return cards[pos];
    }

}