import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Hand implements ActionListener {
    // Define instance variables here
    private Player player;
    private Dealer dealer;
    private int ID;
    private Card[] cards;
    private boolean busted;
    private boolean doubled;
    private boolean splitHand;
    private boolean firstHandAce;
    private int numOfCards;
    private int runningTotal;
    private boolean blackJack;
    private boolean splittable;
    private boolean doublable;
    private boolean standing;
    private int bet;
    private boolean soft;
    Box box;
    // Buttons
    private JButton btnHit;
    private JButton btnDouble;
    private JButton btnSplit;
    private JButton btnStand;

    // Constructor
    public Hand(Player player, int ID, int bet) {
        // Initialize instance variables here
        this.player = player;
        this.ID = ID;
        cards = new Card[22];
        busted = false;
        doubled = false;
        firstHandAce = false;
        blackJack = false;
        splittable = false;
        doublable = true;
        standing = false;
        numOfCards = 0;
        runningTotal = 0;
        soft = false;
        this.bet = bet;
        splitHand = false;

    }

    public Hand(Dealer dealer) {
        // Initialize instance variables here
        this.dealer = dealer;
        this.ID = -1;
        cards = new Card[22];
        busted = false;
        doubled = false;
        firstHandAce = false;
        blackJack = false;
        splittable = false;
        doublable = true;
        standing = false;
        numOfCards = 0;
        runningTotal = 0;
        soft = false;
        this.bet = 0;
        splitHand = false;

    }

    // Add a card to the hand
    public Card addCard() {
        // Implement the logic to add a card to the hand here
        int nextX = 0;
        Card card = BjSimulation.getShoe().getCard();
        if (numOfCards <= 22) {

            if (numOfCards == 0) {
                nextX = box.getXPos();
            } else {
                nextX = cards[numOfCards - 1].getBox().getXPos() + 35;

            }

            card.setBox(new Box(nextX, box.getYPos()));

            cards[numOfCards] = card;
            numOfCards++;
            runningTotal += card.getcardValue();

            return card;

        } else {
            System.out.println("Error card count is above 22. Auto Bust.");
            return null;
        }

    }

    public Card Double() {
        bet += bet;
        Card temp = addCard();
        doubled = true;
        return temp;

    }

    public Card Split() {

        if (splittable) {
            numOfCards--;
            splittable = false;
            setSplitHand(true);

            Card card = cards[player.getNumOfSplitHands()];
            cards[player.getNumOfSplitHands()] = null;

            return card;

        } else {
            return null;
        }

    }

    public int getBet() {
        return bet;
    }

    public void setSplitHand(boolean splitHand) {
        this.splitHand = splitHand;
    }

    public boolean isSplitHand() {
        return splitHand;
    }

    public void Stand() {
        standing = true;
    }

    public boolean busted() {
        return busted;
    }

    public boolean firstCardIsAce() {
        return firstHandAce;
    }

    public boolean isBlackJack() {
        return blackJack;
    }

    public int getHandValue() {
        return runningTotal;
    }

    public boolean isStanding() {
        return standing || busted || doubled;
    }

    public int getNextFreePosition() {
        return numOfCards;
    }

    public boolean isDoubled() {
        return doubled;
    }

    public String toString() {
        String temp = "";
        for (Card card : cards) {
            if (card != null)
                temp += card + "\n";
        }
        if (player != null) {
            return "Player: " + player.getID() + "\nHand: " + ID + "\n" + temp + "Total: " + runningTotal + "\n";
        } else {
            return "Dealer: " + "\nHand: " + ID + "\n" + temp + "Total: " + runningTotal + "\n";
        }

    }

    public void newHand() {

        cards = new Card[22];
        busted = false;
        doubled = false;
        firstHandAce = false;
        blackJack = false;
        splittable = false;
        doublable = true;
        standing = false;
        numOfCards = 0;
        runningTotal = 0;
        soft = false;
        splitHand = false;

    }

    public Card getCard(int pos) {
        return cards[pos];
    }

    public void draw(Graphics2D g2) {

        for (int i = 0; i < numOfCards; i++) {
            if (cards[i] != null)
                if (i == 0 || (i != 0 && cards[i - 1].hasArrived())) {
                    cards[i].draw(g2);

                }
        }

    }

    public void update() {
        if (player != null) {

            if (cards[0] != null && numOfCards == 0 && cards[0].isAce()) {
                firstHandAce = true;
                System.out.println("first card is a ace");
            }
            if (numOfCards == 2 && cards[1] != null && cards[0] != null && cards[0].equalCardValue(cards[1])) {
                splittable = true;
                System.out.println("Splittable cards");
            } else {
                splittable = false;
            }
            if (runningTotal == 21 && numOfCards == 2) {
                blackJack = true;

                System.out.println("BlackJack");
            }
            if (runningTotal > 21) {
                busted = true;

                System.out.println("Busted");
                synchronized (player) {
                    player.notify();

                }
            }

            if (numOfCards == 2) {

                doublable = true;

            } else if (isSplitHand() && numOfCards == 1) {

                doublable = true;

            } else {

                doublable = false;

            }

            if (numOfCards >= 1) {
                btnHit.setVisible(true);
                btnStand.setVisible(true);
                if (splittable) {
                    btnSplit.setVisible(true);
                }
                if (doublable || splitHand) {
                    btnDouble.setVisible(true);
                }
            }

        }

    }

    public int getNumCards() {
        return numOfCards;
    }

    public boolean isSplittable() {
        return splittable;
    }

    public void setBox(Box box) {
        this.box = box;
        int nextX = 0;
        for (int i = 0; i < numOfCards; i++) {
            if (cards[i] != null) {

                if (i == 0) {
                    nextX = box.getXPos();
                } else {
                    nextX = cards[i - 1].getBox().getXPos() + 35;

                }

                cards[i].setBox(new Box(nextX, box.getYPos()));
            }
        }
    }

    public Box getBox() {
        return box;
    }

    public int getXPos() {
        return box.getXPos();
    }

    public int getYPos() {
        return box.getYPos();
    }

    public void addCard(Card card) {
        cards[numOfCards] = card;
        numOfCards++;
    }

    public void setRunningTotal(int runningTotal) {
        this.runningTotal = runningTotal;
    }

    public void makeGUI(DisplayPanel displayPanel) {

        if (btnHit != null) {
            removeButtons(displayPanel);
        }

        int Ybutton = 0;
        // Hit button
        btnHit = new JButton("Hit");
        btnHit.setBounds(getXPos(), getYPos() + Card.cardHeight + Ybutton + 10,
                2 * DisplayPanel.tile, DisplayPanel.tile / 2);
        btnHit.addActionListener(this);
        displayPanel.add(btnHit);
        // Stand button
        Ybutton += DisplayPanel.tile / 2;
        btnStand = new JButton("Stand");
        btnStand.setBounds(getXPos(), getYPos() + Card.cardHeight + Ybutton + 10,
                2 * DisplayPanel.tile, DisplayPanel.tile / 2);
        btnStand.addActionListener(this);
        displayPanel.add(btnStand);
        // Double button
        Ybutton += DisplayPanel.tile / 2;
        btnDouble = new JButton("Double");
        btnDouble.setBounds(getXPos(), getYPos() + Card.cardHeight + Ybutton + 10,
                2 * DisplayPanel.tile, DisplayPanel.tile / 2);
        btnDouble.addActionListener(this);
        displayPanel.add(btnDouble);
        // Split button
        Ybutton += DisplayPanel.tile / 2;
        btnSplit = new JButton("Split");
        btnSplit.setBounds(getXPos(), getYPos() + Card.cardHeight + Ybutton + 10,
                2 * DisplayPanel.tile, DisplayPanel.tile / 2);
        btnSplit.addActionListener(this);
        displayPanel.add(btnSplit);

        btnHit.setVisible(false);
        btnStand.setVisible(false);
        btnDouble.setVisible(false);
        btnSplit.setVisible(false);
    }

    public void removeButtons(DisplayPanel displayPanel) {
        if (displayPanel != null) {

            displayPanel.remove(btnHit);
            displayPanel.remove(btnStand);
            displayPanel.remove(btnDouble);
            displayPanel.remove(btnSplit);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Hit")) {

            Card card = addCard();

            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }

        }

        if (e.getActionCommand().equals("Double")) {

            Card card = Double();

            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }
        }
        if (e.getActionCommand().equals("Split")) {

            BjSimulation.player.split(this);

            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }
        }
        if (e.getActionCommand().equals("Stand")) {

            Stand();
            removeButtons(BjSimulation.displayPanel);
            synchronized (BjSimulation.player) {
                BjSimulation.player.notify();
            }

        }

    }

    public void incNumCards() {
        numOfCards++;
    }

    public void setSplittable(boolean splittable) {
        this.splittable = splittable;
    }

}