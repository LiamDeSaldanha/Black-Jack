import java.awt.Graphics2D;

public class Table {
    private Player[] players;
    Dealer dealer;
    private Shoe shoe;

    public Table(int numOfPlayers, Shoe shoe) {
        this.players = new Player[numOfPlayers];
        this.shoe = shoe;
        this.dealer = new Dealer(new Box(DisplayPanel.windowWidth / 2, DisplayPanel.dealerY));

        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player(i, new Box(DisplayPanel.windowWidth / 2, DisplayPanel.playerY));
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
        for (Player player : players) {
            player.getBox().draw(g2);
        }
        dealer.getBox().draw(g2);
    }

}
