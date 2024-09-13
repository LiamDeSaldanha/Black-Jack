import java.util.concurrent.atomic.AtomicBoolean;

public class Counter extends Thread {
    int ID;
    int Xdes;
    int Ydes;
    int currentX = Shoe.deckX;
    int currentY = Shoe.deckY;
    static AtomicBoolean running = new AtomicBoolean();
    Card card;
    static int Dealercount;
    static int Playercount;

    Counter(Card card) {
        this.card = card;
        Xdes = card.Xpos;
        Ydes = card.Ypos;

    }

    @Override
    public void run() {
        while (running.get()) {
            synchronized (card) {
            }
        }
        running.set(true);
        moveCard();

        Playercount++;
        running.set(false);
        synchronized (card) {
            if (BjSimulation.round.player.getHand(0).getCard(Playercount) != null)
                synchronized (BjSimulation.round.player.getHand(0).getCard(Playercount)) {
                    BjSimulation.round.player.getHand(0).getCard(Playercount).notify();

                }

        }

        System.out.println(card + " X: " + currentX);

    }

    public synchronized void moveCard() {

        while (currentY != Ydes) {
            currentY++;

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        while (currentX != Xdes) {
            currentX--;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        running.set(false);

        card.arrived();

    }

}
