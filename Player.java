import java.awt.Graphics2D;

public class Player {
    private int ID;
    private Hand[] hands;
    private int numOfHands;
    private boolean donePlaying;
    private int numOfSplitHands;
    private Box box;

    public Player(int ID, Box box) {
        this.ID = ID;
        hands = new Hand[28];
        numOfHands = 0;

        hands[0] = new Hand(this, 0, 0);

        hands[0].setBox(box);
        numOfHands++;
        numOfSplitHands = 0;
        this.box = box;
    }

    public int getNumHands() {
        return numOfHands;
    }

    public void newRound() {

        for (int i = 0; i < hands.length; i++) {
            if (hands[i] != null) {
                hands[i].removeButtons(BjSimulation.displayPanel);
                hands[i] = null;

            }
        }
        numOfHands = 0;
        numOfSplitHands = 0;
        hands[0] = new Hand(this, getID(), 0);
        hands[0].setBox(box);
        numOfHands++;
    }

    public Hand getHand(int ID) {
        return hands[ID];
    }

    public int getID() {
        return ID;
    }

    public void split(Hand hand) {
        numOfSplitHands++;
        Hand newHand = new Hand(this, getID() + 1, hand.getBet());
        Card card2 = hand.getCard(1);
        hand.Split();

        newHand.addCard(card2);
        hands[numOfSplitHands] = newHand;
        hands[numOfSplitHands].setSplitHand(true);

        hands[numOfSplitHands].setBox(new Box((int) (hand.getXPos() + (Card.cardWidth * 0.7)), hand.getYPos()));
        hand.setBox(new Box((int) (hand.getXPos() - (Card.cardWidth * 0.7)), hand.getYPos()));
        hand.setRunningTotal(0);
        hand.setRunningTotal(hand.getCard(0));
        hand.makeGUI(BjSimulation.displayPanel);
        hands[numOfSplitHands].makeGUI(BjSimulation.displayPanel);
        hand.addCard();
        hands[numOfSplitHands].addCard();
        numOfHands++;
    }

    public void update() {

        for (int i = 0; i < numOfHands; i++) {
            if (hands[i] != null) {
                hands[i].update();
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < numOfHands; i++) {
            if (hands[i] != null) {

                hands[i].draw(g2);
            }
        }
    }

    public boolean isDonePlaying() {
        boolean temp = true;

        for (int i = 0; i < hands.length; i++) {
            if (hands[i] != null) {
                if (!hands[i].isStanding()) {
                    temp = false;
                }
            }
        }

        return temp;
    }

    public void decrementNumofHands() {
        numOfHands--;
    }

    public int getNumOfSplitHands() {
        return numOfSplitHands;
    }

}
