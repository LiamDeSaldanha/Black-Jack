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

    public void getStats() {

        System.out.println("Dealer: ");
        System.out.println("================");
        System.out.println(dealer.getHand());

        System.out.println("Player: " + player.getID());
        System.out.println("================");
        for (int i = 0; i < player.getNumHands(); i++) {
            System.out.println(player.getHand(i));

            if (player.getHand(i).busted())

            {
                DisplayPanel.lblWinner.setText("Dealer wins");
                System.out.println("Dealer wins");
            } else if (dealer.getHand().getHandValue() > 21) {
                DisplayPanel.lblWinner.setText("Player wins");
                System.out.println("Player wins");
            } else if (dealer.getHand().getHandValue() > player.getHand(i).getHandValue()) {
                DisplayPanel.lblWinner.setText("Dealer wins");
                System.out.println("Dealer wins");
            } else if (dealer.getHand().getHandValue() < player.getHand(i).getHandValue()) {
                DisplayPanel.lblWinner.setText("Player wins");
                System.out.println("Player wins");
            } else if (dealer.getHand().hasBJ() && player.getHand(i).hasBJ()) {
                DisplayPanel.lblWinner.setText("Standoff");
                System.out.println("Standoff");

            }
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
