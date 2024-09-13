import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Card {

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

    private int cardValue;
    private Suit suit;
    private boolean courtCard;
    private String suitString;
    private String cardValueString;
    private boolean arrived;
    final static int cardHeight = 3 * DisplayPanel.tile;
    final static int cardWidth = 2 * DisplayPanel.tile;
    int Xpos = Shoe.deckX;
    int Ypos = Shoe.deckY;
    Box box;
    Counter time;
    boolean timerStarted;

    public Card(int cardValue, Suit suit) {
        this.cardValue = cardValue;
        this.suit = suit;
        courtCard = false;
        this.suitString = suit.getSymbol();
        this.cardValueString = Integer.toString(cardValue);
        arrived = false;

    }

    public Card(int cardValue, Suit suit, boolean courtCard) {
        this.cardValue = cardValue;
        this.suit = suit;
        this.courtCard = courtCard;
        this.suitString = suit.getSymbol();
        this.cardValueString = Integer.toString(cardValue);
        arrived = false;
    }

    public int getcardValue() {
        return cardValue;
    }

    public void arrived() {
        arrived = true;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isCourtCard() {
        return courtCard;
    }

    public boolean equals(Card card) {
        if (card != null) {
            if (this.cardValue == card.getcardValue() && this.suit == card.getSuit()
                    && this.courtCard == card.isCourtCard()) {
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
            if (this.cardValue == card.getcardValue()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isAce() {
        if (this.cardValue == 1 && this.isCourtCard()) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Card added: " + cardValue + " " + suit + " Face card: " + courtCard;
    }

    public String getCardSuit() {

        return suitString;
    }

    public String getCardcardValue() {
        return cardValueString;
    }

    public void update() {

    }

    public boolean hasArrived() {
        return arrived;
    }

    public void draw(Graphics2D g2) {

        if (Ypos != box.getYPos()) {

            g2.setColor(new Color(200, 0, 0)); // Red color for the back of the card
            g2.fillRoundRect(Xpos, Ypos, Card.cardWidth, Card.cardHeight,
                    10, 10); // Draw rounded
            // rectangle

            // Draw the card border
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(Xpos, Ypos, Card.cardWidth, Card.cardHeight,
                    10, 10); // Draw white
            Ypos += 20;
            if (Ypos > box.getYPos()) {
                Ypos = box.getYPos();
            }
        }

        if (Xpos != box.getXPos()) {
            g2.setColor(new Color(200, 0, 0)); // Red color for the back of the card
            g2.fillRoundRect(Xpos, Ypos, Card.cardWidth, Card.cardHeight,
                    10, 10); // Draw rounded
            // rectangle

            // Draw the card border
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(Xpos, Ypos, Card.cardWidth, Card.cardHeight,
                    10, 10); // Draw white
            Xpos -= 20;
            if (Xpos < box.getXPos()) {
                Xpos = box.getXPos();
            }
        }

        if (Xpos == box.getXPos() && Ypos == box.getYPos()) {
            arrived();
            g2.setPaint(Color.WHITE);
            g2.fillRoundRect(Xpos, Ypos, cardWidth, cardHeight, 10, 10);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 28));
            if (suitString.equals("♥")
                    || suitString.equals("♦")) {
                g2.setColor(Color.RED);
            }
            g2.drawString(cardValueString,
                    Xpos + 5, Ypos + 30); // Top-left

            g2.drawString(suitString,
                    Xpos + 5, Ypos + 50); // Top-right

        }
        if (Xpos > DisplayPanel.windowWidth || Xpos < 0 || Ypos < 0 || Ypos > DisplayPanel.windowHeight) {
            System.out.println("Card out of bounds X:" + Xpos + " Y: " + Ypos);
        }

    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Box getBox() {
        return box;
    }

}
