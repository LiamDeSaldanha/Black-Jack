import java.awt.Graphics2D;

public class Round {
    Player player;
    Dealer dealer;
    boolean roundStarted;
    // static int numOfCardsDealer = 0;
    // static int numOfCardsPlayer = 0;

    public Round(Player player) {
        this.player = player;
        dealer = BjSimulation.dealer;

    }

    public void startRound() {

        roundStarted = true;
        DisplayPanel.startRound();
        player.getHand(0).makeGUI(BjSimulation.displayPanel);

        player.getHand(0).addCard();
        dealer.getHand().addCard();
        player.getHand(0).addCard();
        synchronized (player) {
            player.notify();

        }

    }

    public void endRound() {

        dealer.getHand().newHand();
        player.newRound();
        roundStarted = false;
        DisplayPanel.endRound();

    }

    public void update() {
        player.update();
        dealer.update();
    }

    public void draw(Graphics2D g2) {

        player.draw(g2);

        dealer.draw(g2);

    }
}
