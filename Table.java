import java.awt.Graphics2D;

public class Table {
    private Player[] players;
    Dealer dealer;
    private Shoe shoe;
    final static Box box1 = new Box(DisplayPanel.tile, DisplayPanel.playerY);
    final static Box box2 = new Box((DisplayPanel.tile * 2 * 2) + (Card.cardWidth), DisplayPanel.playerY);
    final static Box box3 = new Box((DisplayPanel.tile * 3 * 2) + (Card.cardWidth * 2), DisplayPanel.playerY);
    final static Box box4 = new Box((DisplayPanel.tile * 4 * 2) + (Card.cardWidth * 3), DisplayPanel.playerY);
    final static Box box5 = new Box((DisplayPanel.tile * 5 * 2) + (Card.cardWidth * 4), DisplayPanel.playerY);
   // final static Box box6 = new Box((DisplayPanel.tile * 6 * 2) + (Card.cardWidth * 5), DisplayPanel.playerY);

    public Table(int numOfPlayers, Shoe shoe) {
        this.players = new Player[numOfPlayers];
        this.shoe = shoe;
        this.dealer = new Dealer(new Box(DisplayPanel.windowWidth / 2, DisplayPanel.dealerY));

        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player(i, box3);
        }

    }

    public Shoe getShoe() {
        return shoe;
    }

    public Player getPlayer(int ID) {
        return players[ID];
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void draw(Graphics2D g2) {
        box1.draw(g2);
        box2.draw(g2);
        box3.draw(g2);
        box4.draw(g2);
        box5.draw(g2);
    //    box6.draw(g2);

        dealer.getBox().draw(g2);
    }

}
