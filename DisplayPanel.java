import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel implements Runnable, ActionListener {

    private final static int originalTile = 16;
    private final static int scalingFactor = 3;
    private final static int tile = originalTile * scalingFactor;
    private final static int rowNoOfWindow = 12;
    private final static int colNoOfWindow = 24;
    private final static int windowWidth = colNoOfWindow * tile;// 768
    private final static int windowHeight = rowNoOfWindow * tile;// 576
    private final static int cardHeight = 3 * tile;
    private final static int cardWidth = 2 * tile;
    private final static int dealerY = tile;

    private final static int playerY = dealerY + tile + cardHeight;

    static int numOfCardsDealer = 0;
    static int numOfCardsPlayer = 0;

    Thread gameLoop;
    // GUI
    static Graphics2D g2;
    static JButton btnHit;
    static JButton btnDouble;
    static JButton btnSplit;
    static JButton btnStand;
    // temp gui stuff
    static String suit = "";
    static String rank = "";
    static boolean login;
    static JButton btnPlay;
    static JLabel lblWinner = new JLabel("");
    static boolean displayWinner;
    // temp track cards

    static int[] playerCards = new int[6];
    static int[] dealerCards = new int[6];

    static int nextPlayerCardX = tile;
    static int PlayerCardY = tile + cardHeight;
    static int nextDealerCardX = tile;
    static int DealerCardY = tile;
    static int deckX = windowWidth - cardWidth - tile;
    static int deckY = dealerY;
    static int currentX;
    static int currentY;
    // moving cards
    static Counter[] time = new Counter[6];
    static Counter[] timeDealer = new Counter[6];

    // FPS
    private final int FPS = 60;

    public DisplayPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.setBackground(new Color(50, 86, 86));
        this.setDoubleBuffered(true);
        this.addGUI();
        btnHit.setVisible(false);
        btnDouble.setVisible(false);
        btnSplit.setVisible(false);
        btnStand.setVisible(false);
        btnPlay = new JButton("Play");
        btnPlay.setBounds((7 * tile + 6 * tile) / 2, windowHeight - (3 * tile), 2 * tile, tile / 2);
        btnPlay.addActionListener(this);
        this.add(btnPlay);
        lblWinner.setFont(new Font("Arial", Font.BOLD, 72));
        lblWinner.setBounds(windowWidth / 2 - 200, (windowHeight / 2) - 100, 500, 80);
        this.add(lblWinner);
        lblWinner.setVisible(false);

    }

    public void startGameLoop() {
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public static void addCardDisplay(Card card, boolean dealer) {

        if (numOfCardsDealer > 0) {
            nextDealerCardX += tile + cardWidth;
        }
        dealerCards[numOfCardsDealer] = nextDealerCardX;
        timeDealer[numOfCardsDealer] = new Counter(dealerCards[numOfCardsDealer], dealerY, numOfCardsDealer);
        timeDealer[numOfCardsDealer].start();
        if (dealer) {
            numOfCardsDealer++;
        }

    }

    public static void addCardDisplay(Card card) {

        if (numOfCardsPlayer > 0) {
            nextPlayerCardX += cardWidth + tile;
        }
        playerCards[numOfCardsPlayer] = nextPlayerCardX;
        time[numOfCardsPlayer] = new Counter(playerCards[numOfCardsPlayer], playerY, numOfCardsPlayer);
        time[numOfCardsPlayer].start();

        numOfCardsPlayer++;

    }

    public void addGUI() {
        // Hit button
        btnHit = new JButton("Hit");
        btnHit.setBounds(2 * tile, windowHeight - (2 * tile), 2 * tile, tile / 2);
        btnHit.addActionListener(this);
        this.add(btnHit);
        // Double button
        btnDouble = new JButton("Double");
        btnDouble.setBounds(2 * tile + (3 * tile), windowHeight - (2 * tile), 2 * tile, tile / 2);
        btnDouble.addActionListener(this);
        this.add(btnDouble);
        // Split button
        btnSplit = new JButton("Split");
        btnSplit.setBounds(2 * tile + (3 * tile * 2), windowHeight - (2 * tile), 2 * tile, tile / 2);
        btnSplit.addActionListener(this);
        this.add(btnSplit);
        // Stand button
        btnStand = new JButton("Stand");
        btnStand.setBounds(2 * tile + (3 * tile * 3), windowHeight - (2 * tile), 2 * tile, tile / 2);
        btnStand.addActionListener(this);
        this.add(btnStand);

    }

    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameLoop != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (displayWinner) {
            lblWinner.setVisible(true);

        } else {
            lblWinner.setVisible(false);
        }

        for (int i = 0; i < numOfCardsPlayer; i++) {
            if (BjSimulation.player.getHand(0).getCard(i) != null && !time[i].running) {
                g2.setPaint(Color.WHITE);
                g2.fillRoundRect(playerCards[i], playerY, cardWidth, cardHeight, 10, 10);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 36));
                if (BjSimulation.player.getHand(0).getCard(i).getCardSuit().equals("♥")
                        || BjSimulation.player.getHand(0).getCard(i).getCardSuit().equals("♦")) {
                    g2.setColor(Color.RED);
                }
                if (BjSimulation.player.getHand(0).getCard(i).getCardRank() != null
                        && BjSimulation.player.getHand(0).getCard(i).getCardSuit() != null) {

                    g2.drawString(BjSimulation.player.getHand(0).getCard(i).getCardRank(),
                            playerCards[i] + 35, playerY + 80); // Top-left
                    g2.drawString(BjSimulation.player.getHand(0).getCard(i).getCardSuit(),
                            playerCards[i], playerY + 30); // Top-right

                }
            }
        }

        for (int i = 0; i < numOfCardsDealer; i++) {
            if (BjSimulation.dealer.getHand().getCard(i) != null && !timeDealer[i].running) {

                g2.setPaint(Color.WHITE);
                g2.fillRoundRect(dealerCards[i], dealerY, cardWidth, cardHeight, 10, 10);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 36));
                if (BjSimulation.dealer.getHand().getCard(i).getCardSuit().equals("♥")
                        || BjSimulation.dealer.getHand().getCard(i).getCardSuit().equals("♦")) {
                    g2.setColor(Color.RED);
                }
                if (BjSimulation.dealer.getHand().getCard(i).getCardRank() != null
                        && BjSimulation.dealer.getHand().getCard(i).getCardSuit() != null) {
                    g2.drawString(BjSimulation.dealer.getHand().getCard(i).getCardRank(),
                            dealerCards[i] + 35, dealerY + 80); // Top-left
                    // rank
                    g2.drawString(BjSimulation.dealer.getHand().getCard(i).getCardSuit(),
                            dealerCards[i], dealerY + 30); // Top-right
                    // suit
                }
            }
        }

        //////////
        // Set anti-aliasing for smoother lines

        // Draw the red background
        g2.setColor(new Color(200, 0, 0)); // Red color for the back of the card
        g2.fillRoundRect(deckX, deckY, cardWidth, cardHeight, 10, 10); // Draw rounded
        // rectangle

        // Draw the card border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(deckX, deckY, cardWidth, cardHeight, 10, 10); // Draw white
        // border

        // g2.dispose();
        for (int i = 0; i < numOfCardsPlayer; i++) {

            if (time[i].currentX != playerCards[i]) {
                g2.setColor(new Color(200, 0, 0)); // Red color for the back of the card
                g2.fillRoundRect(time[i].currentX, time[i].currentY, cardWidth, cardHeight,
                        10, 10); // Draw rounded
                // rectangle

                // Draw the card border
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(5));
                g2.drawRoundRect(time[i].currentX, time[i].currentY, cardWidth, cardHeight,
                        10, 10); // Draw white

            }

        }
        for (int i = 0; i < numOfCardsDealer; i++) {

            if (timeDealer[i].currentX != dealerCards[i]) {
                g2.setColor(new Color(200, 0, 0)); // Red color for the back of the card
                g2.fillRoundRect(timeDealer[i].currentX, timeDealer[i].currentY, cardWidth, cardHeight,
                        10, 10); // Draw rounded
                // rectangle

                // Draw the card border
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(5));
                g2.drawRoundRect(timeDealer[i].currentX, timeDealer[i].currentY, cardWidth, cardHeight,
                        10, 10); // Draw white

            }

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Hit")) {
            BjSimulation.setChoice(1);
            Card card = BjSimulation.player.getHand(0).addCard();

            addCardDisplay(card);

            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }

        }
        if (e.getActionCommand().equals("Double")) {
            BjSimulation.setChoice(1);
            BjSimulation.player.getHand(0).Double();

            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }
        }
        if (e.getActionCommand().equals("Split")) {
            BjSimulation.setChoice(1);
            BjSimulation.player.getHand(0).Split();

            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }
        }
        if (e.getActionCommand().equals("Stand")) {
            BjSimulation.setChoice(1);
            BjSimulation.player.getHand(0).Stand();
            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }

        }

        if (e.getActionCommand().equals("Play")) {
            BjSimulation.setChoice(1);
            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }
            btnPlay.setVisible(false);
            btnHit.setVisible(true);
            btnDouble.setVisible(true);
            btnSplit.setVisible(true);
            btnStand.setVisible(true);
            displayWinner = false;

        }

    }

    public static void playAgain() {

        reset();

    }

    public static void reset() {
        playerCards = new int[6];
        dealerCards = new int[6];

        nextPlayerCardX = tile;
        PlayerCardY = tile + cardHeight;
        nextDealerCardX = tile;
        DealerCardY = tile;
        suit = "";
        rank = "";
        numOfCardsDealer = 0;
        numOfCardsPlayer = 0;

    }

    public static void resetButtons() {
        btnHit.setVisible(false);
        btnDouble.setVisible(false);
        btnSplit.setVisible(false);
        btnStand.setVisible(false);
        btnPlay.setVisible(true);
    }

}
