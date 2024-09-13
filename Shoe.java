import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Shoe {
    private int numOfDecks;
    private Card[] shoe;
    private int currentCardCount;
    static int deckX = DisplayPanel.windowWidth - Card.cardWidth - DisplayPanel.tile;
    static int deckY = DisplayPanel.dealerY;

    public Shoe(int numOfDecks) {
        currentCardCount = 52 * numOfDecks;
        shoe = new Card[currentCardCount];

        this.numOfDecks = numOfDecks;
        int index = 0;
        for (int i = 0; i < numOfDecks; i++) {
            Deck deck = new Deck();
            for (Card card : deck.getCards()) {
                shoe[index] = card;
                index++;
            }

        }

        // Fisher-Yates shuffle algorithm

        Random rand = new Random();
        for (int i = shoe.length - 1; i > 0; i--) {
            // Generate a random index between 0 and i
            int j = rand.nextInt(i + 1);

            // Swap array[i] and array[j]
            Card temp = shoe[i];
            shoe[i] = shoe[j];
            shoe[j] = temp;
        }

    }

    public Card getCard() {

        if (currentCardCount != 0) {

            Card temp = shoe[currentCardCount - 1];
            shoe[currentCardCount - 1] = null;
            currentCardCount--;
            return temp;

        } else {
            return null;
        }

    }

    public void draw(Graphics2D g2) {
        // Draw the red background
        g2.setColor(new Color(200, 0, 0)); // Red color for the back of the card
        g2.fillRoundRect(deckX, deckY, Card.cardWidth, Card.cardHeight, 10, 10); // Draw rounded
        // rectangle

        // Draw the card border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(deckX, deckY, Card.cardWidth, Card.cardHeight, 10, 10); // Draw white
    }


}
