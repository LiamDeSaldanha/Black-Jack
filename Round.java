import java.awt.Graphics2D;

public class Round {
    Player player;
    Dealer dealer;
    boolean roundStarted;
    static int numOfCardsDealer = 0;
    static int numOfCardsPlayer = 0;

    public Round(Player player) {
        this.player = player;
        dealer = BjSimulation.dealer;

    }

    public void startRound() {
        synchronized (player) {
            player.notify();

        }

        roundStarted = true;
        DisplayPanel.startRound();
        addCardDisplay(player.getHand(0).addCard());
        addCardDisplay(dealer.getHand().addCard(), true);
        addCardDisplay(player.getHand(0).addCard());
    }

    public void endRound() {
        numOfCardsDealer = 0;
        numOfCardsPlayer = 0;
        dealer.getHand().newHand();
        player.getHand(0).newHand();
        roundStarted = false;
        DisplayPanel.endRound();

    }

    public void addCardDisplay(Card card, boolean dealer) {
        int nextX = 0;
        if (numOfCardsDealer > 0) {

            nextX = this.dealer.getHand().getCard(numOfCardsDealer - 1).getBox().getXPos() + 35;

        } else {
            nextX = this.dealer.getHand().getXPos();
        }
        card.setBox(new Box(nextX, this.dealer.getHand().getYPos()));

        if (dealer) {
            numOfCardsDealer++;
        }
        // card.startTimer();

    }

    public void addCardDisplay(Card card) {
        int nextX = 0;
        if (numOfCardsPlayer > 0) {

            nextX = player.getHand(0).getCard(numOfCardsPlayer - 1).getBox().getXPos() + 35;
            ;
        } else {
            nextX = player.getHand(0).getXPos();
        }
        card.setBox(new Box(nextX, player.getHand(0).getYPos()));

        numOfCardsPlayer++;
        // card.startTimer();

    }

    public void update() {

    }

    public void draw(Graphics2D g2) {
        if (player.getNumHands() > 1) {
            for (int i = 0; i < player.getNumHands(); i++) {
                player.getHand(i).draw(g2);
            }
        } else {
            player.getHand(0).draw(g2);
        }

        dealer.getHand().draw(g2);

    }
}
