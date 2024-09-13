import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel implements Runnable, ActionListener {

    final static int originalTile = 16;
    final static int scalingFactor = 3;
    final static int tile = originalTile * scalingFactor;
    final static int rowNoOfWindow = 12;
    final static int colNoOfWindow = 24;
    final static int windowWidth = colNoOfWindow * tile;// 768
    final static int windowHeight = rowNoOfWindow * tile;// 576

    final static int dealerY = tile;

    final static int playerY = dealerY + tile + Card.cardHeight;

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

    static int currentX;
    static int currentY;
    // moving cards

    // FPS
    final int FPS = 60;

    // Multiplayer set up phase 1
    Box box = new Box(tile, playerY);

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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // draw deck
        BjSimulation.table.getShoe().draw(g2);
        BjSimulation.table.draw(g2);

        BjSimulation.round.draw(g2);

        // g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Hit")) {

            Card card = BjSimulation.player.getHand(0).addCard();

            BjSimulation.round.addCardDisplay(card);

            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }

        }
        if (e.getActionCommand().equals("Double")) {

            Card card = BjSimulation.player.getHand(0).Double();
            BjSimulation.round.addCardDisplay(card);
            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }
        }
        if (e.getActionCommand().equals("Split")) {

            BjSimulation.player.split(BjSimulation.player.getHand(0));

            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }
        }
        if (e.getActionCommand().equals("Stand")) {

            BjSimulation.player.getHand(0).Stand();
            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }

        }

        if (e.getActionCommand().equals("Play")) {

            BjSimulation.round.startRound();

        }

    }

    public static void startRound() {
        btnPlay.setVisible(false);
        btnHit.setVisible(true);
        btnDouble.setVisible(true);
        // btnSplit.setVisible(true);
        btnStand.setVisible(true);
        displayWinner = false;

    }

    public static void endRound() {

        btnHit.setVisible(false);
        btnDouble.setVisible(false);
        btnSplit.setVisible(false);
        btnStand.setVisible(false);
        btnPlay.setVisible(true);
    }

    public static void hideGui() {
        btnHit.setVisible(false);
        btnDouble.setVisible(false);
        btnSplit.setVisible(false);
        btnStand.setVisible(false);
    }

}
