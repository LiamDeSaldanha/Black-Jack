import java.awt.Graphics2D;

public class Player {
    private int ID;
    private Hand[] hands;
    private int numOfHands;
    private boolean donePlaying;

    public Player(int ID, Box box) {
        this.ID = ID;
        hands = new Hand[28];
        numOfHands = 0;

        hands[0] = new Hand(this, 0, 0);

        hands[0].setBox(box);
        numOfHands++;
    }

    public int getNumHands() {
        return numOfHands;
    }

    public void newRound() {
        Box box = hands[0].getBox();
        for (int i = 0; i < hands.length; i++) {
            if (hands[i] != null) {
                hands[i].removeButtons(BjSimulation.displayPanel);
                hands[i] = null;

            }
        }
        hands[0] = new Hand(this, getID(), 0);
        hands[0].setBox(box);
    }

    public Hand getHand(int ID) {
        return hands[ID];
    }

    public int getID() {
        return ID;
    }

    public void split(Hand hand) {
        Hand newHand = new Hand(this, getID() + 1, hand.getBet());
        Card card2 = hand.getCard(1);
        hand.Split();

        newHand.addCard(card2);
        hands[1] = newHand;
        hands[1].setSplitHand(true);

        hands[1].setBox(new Box((int) (hands[0].getXPos() + (Card.cardWidth * 0.7)), hands[0].getYPos()));
        hands[0].setBox(new Box((int) (hands[0].getXPos() - (Card.cardWidth * 0.7)), hands[0].getYPos()));

        hands[0].setRunningTotal(hands[0].getCard(0).getcardValue());
        hands[0].makeGUI(BjSimulation.displayPanel);
        hands[1].makeGUI(BjSimulation.displayPanel);
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

}
