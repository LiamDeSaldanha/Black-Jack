public class Player {
    private int ID;
    private Hand[] hands;
    private int numOfHands;

    public Player(int ID) {
        this.ID = ID;
        hands = new Hand[28];
        numOfHands = 0;
        for (int i = 0; i < 28; i++) {
            hands[i] = new Hand(this.ID, i, 0);

        }

    }

    public Player() {

    }

    public Hand getHand(int ID) {
        return hands[ID];
    }

    public int getID() {
        return ID;
    }

}
