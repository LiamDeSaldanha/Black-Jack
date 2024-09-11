import java.util.Scanner;

import javax.swing.JFrame;

public class BjSimulation {
    private static Table table;

    public static Shoe getShoe() {
        return table.getShoe();
    }

    private static final int HIT = 1;
    private static final int DOUBLE = 2;
    private static final int SPLIT = 3;
    private static final int STAND = 4;
    private static int choice;
    static Player player;
    static Dealer dealer;

    public static void main(String[] args) throws InterruptedException {
        boolean login = false;
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Black Jack");
        DisplayPanel displayPanel = new DisplayPanel();
        window.add(displayPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        displayPanel.startGameLoop();

        table = new Table(1, new Shoe(6));
        dealer = new Dealer();
        Scanner sc = new Scanner(System.in);
        choice = 0;
        player = table.getPlayer(0);
        while (choice != -1) {

            System.out.println("Welcome\n" + "==========================\n" + "-1: Quit\n1: Play");
            try {
                synchronized (player) {
                    if (!login) {
                        player.wait();
                        login = true;
                    }

                }

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            // choice = sc.nextInt();
            // sc.nextLine();

            // if (choice == 1) {
            DisplayPanel.addCardDisplay(player.getHand(0).addCard());
            System.out.println("Player: " + player.getHand(0));
            Thread.sleep(1500);
            DisplayPanel.addCardDisplay(dealer.getHand().addCard(), true);
            System.out.println("Dealer: " + "\nCard: " + dealer.getHand());
            Thread.sleep(1500);
            DisplayPanel.addCardDisplay(player.getHand(0).addCard());
            System.out.println("Player: " + player.getHand(0));

            while (choice != -1 && !player.getHand(0).isStanding() && !player.getHand(0).busted()
                    && !player.getHand(0).isDoubled()) {

                System.out.println("1.Hit");
                System.out.println("2.Double");
                System.out.println("3.Split");
                System.out.println("4.Stand");
                // choice = sc.nextInt();
                // sc.nextLine();
                try {
                    synchronized (player) {
                        player.wait();
                    }

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                switch (choice) {
                    case HIT:
                        // player.getHand(0).addCard();

                        break;
                    case DOUBLE:
                        // player.getHand(0).Double();

                        break;
                    case SPLIT:
                        // player.getHand(0).Split();
                        break;
                    case STAND:
                        // player.getHand(0).Stand();

                        break;

                    default:
                        System.out.println("Invalid choice. Try again");

                        break;
                }

            }
            dealer.Play();
            Thread.sleep(2000);
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
            DisplayPanel.resetButtons();
            try {
                synchronized (player) {
                    player.wait();
                }

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            DisplayPanel.playAgain();

            dealer.getHand().newHand();
            player.getHand(0).newHand();

        }
        sc.close();
        System.out.println("Leaving Table");

    }

    public static void setChoice(int choice) {
        BjSimulation.choice = choice;
    }

}
