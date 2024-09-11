public class Dealer {
    private Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void Play() {

        DisplayPanel.addCardDisplay(hand.addCard(), true);

        while (hand.getHandValue() <= 16) {
            DisplayPanel.addCardDisplay(hand.addCard(), true);
        }

    }

}
