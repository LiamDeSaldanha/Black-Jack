public class Dealer {
    private Hand hand;
    Box box;

    public Dealer(Box box) {
        hand = new Hand();
        this.box = box;
        hand.setBox(box);
    }

    public int getXPos() {
        return box.getXPos();
    }

    public int getYPos() {
        return box.getYPos();
    }

    public Box getBox() {
        return box;
    }

    public Hand getHand() {
        return hand;
    }

    public void Play() {

        BjSimulation.round.addCardDisplay(hand.addCard(), true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        while (hand.getHandValue() <= 16) {
            BjSimulation.round.addCardDisplay(hand.addCard(), true);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

    }

}
