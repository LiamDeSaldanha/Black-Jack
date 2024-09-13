public class Player {
    private int ID;
    private Hand[] hands;
    private int numOfHands;

    Box box;
    boolean standing;

    public Player(int ID, Box box) {
        this.ID = ID;
        hands = new Hand[28];
        numOfHands = 0;
        for (int i = 0; i < 28; i++) {
            hands[i] = new Hand(this.ID, i, 0);

        }

        this.box = box;
        hands[0].setBox(box);
        numOfHands++;
    }

    public int getNumHands() {
        return numOfHands;
    }

    public Box getBox() {
        return box;
    }

    public Player() {

    }

    public Hand getHand(int ID) {
        return hands[ID];
    }

    public int getID() {
        return ID;
    }

    public int getXBox() {
        return box.getXPos();
    }

    public int getYBox() {
        return box.getYPos();
    }

    public boolean isStanding() {
        boolean temp = true;
        for (Hand hand : hands) {
            if (!hand.isStanding()) {
                temp = false;
            }
        }

        return temp;
    }

    public void split(Hand hand) {

        Hand temp = new Hand();
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        temp.addCard(card2);
        hands[1] = temp;

        hands[0].setBox(new Box((int) (box.getXPos() - (Card.cardWidth * 0.7)), box.getYPos()));
        hands[1].setBox(new Box((int) (box.getXPos() + (Card.cardWidth * 0.7)), box.getYPos()));
        numOfHands++;
    }

}
