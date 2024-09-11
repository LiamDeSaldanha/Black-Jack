public class Table {
    private Player[] players;
    private Shoe shoe;

    public Table(int numOfPlayers, Shoe shoe) {
        this.players = new Player[numOfPlayers];
        this.shoe = shoe;

        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player(i);
        }

    }

    public Shoe getShoe() {
        return shoe;
    }

    public Player getPlayer(int ID) {
        return players[ID];
    }

}
