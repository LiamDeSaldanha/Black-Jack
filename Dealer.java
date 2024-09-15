import java.awt.Graphics2D;

public class Dealer {
    private Hand hand;
    private int numOfCards;

    public Dealer(Box box) {
        hand = new Hand(this);
        setBox(box);
        hand.setBox(box);
        numOfCards = 0;
    }

    public void setBox(Box box) {
        hand.setBox(box);
    }

    public int getXPos() {
        return hand.getXPos();
    }

    public int getYPos() {
        return hand.getYPos();
    }

    public Box getBox() {
        return hand.getBox();
    }

    public Hand getHand() {
        return hand;
    }

    public void Play() {
        hand.addCard();
      //  BjSimulation.round.addCardDisplay(hand.addCard(), true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        while (hand.getHandValue() <= 16) {
            hand.addCard();
         //   BjSimulation.round.addCardDisplay(hand.addCard(), true);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

    }

    public void update() {
        hand.update();
    }

    public void draw(Graphics2D g2) {
        hand.draw(g2);
    }

}
