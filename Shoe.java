import java.util.Random;

public class Shoe {
    private int numOfDecks;
    private Card[] shoe;
    private int currentCardCount;

    public Shoe(int numOfDecks) {
        currentCardCount = 52 * numOfDecks;
        shoe = new Card[currentCardCount];

        this.numOfDecks = numOfDecks;
        int index = 0;
        for (int i = 0; i < numOfDecks; i++) {

            for (Card card : Card.values()) {
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

}
