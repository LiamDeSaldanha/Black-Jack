import java.util.Scanner;

import javax.swing.JFrame;

public class BjSimulation {
    static Table table;

    public static Shoe getShoe() {
        return table.getShoe();
    }

    static DisplayPanel displayPanel;
    static Player player;
    static Dealer dealer;
    static Round round;
    static boolean roundPlaying;

    public static void main(String[] args) throws InterruptedException {
        table = new Table(1, new Shoe(6));
        dealer = table.getDealer();
        player = table.getPlayer(0);
        round = new Round(player);
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Black Jack");
        displayPanel = new DisplayPanel();
        window.add(displayPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        displayPanel.startGameLoop();

        while (true) {

            System.out.println("Welcome\n" + "==========================\n");

            while (!player.isDonePlaying()) {
                System.out.println(player.isDonePlaying());
                try {
                    synchronized (player) {
                        System.out.println("waiting for play");

                        player.wait();
                        roundPlaying = true;

                    }

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }

            if (!player.getHand(0).busted()) {
                // DisplayPanel.hideGui();
                dealer.Play();

            }

            System.out.println("Dealer: " + dealer.getHand());
            System.out.println("PLayer: " + player.getHand(0));
            if (player.getHand(0).busted()) {
                DisplayPanel.lblWinner.setText("Dealer wins");
                System.out.println("Dealer wins");
            } else if (dealer.getHand().getHandValue() > 21) {
                DisplayPanel.lblWinner.setText("Player wins");
                System.out.println("Player wins");
            } else if (dealer.getHand().getHandValue() > player.getHand(0).getHandValue()) {
                DisplayPanel.lblWinner.setText("Dealer wins");
                System.out.println("Dealer wins");
            } else if (dealer.getHand().getHandValue() < player.getHand(0).getHandValue()) {
                DisplayPanel.lblWinner.setText("Player wins");
                System.out.println("Player wins");
            } else {
                DisplayPanel.lblWinner.setText("Standoff");
                System.out.println("Standoff");

            }
            DisplayPanel.displayWinner = true;

            round.endRound();
            roundPlaying = false;

        }

    }

}
