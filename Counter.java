public class Counter extends Thread {
    int ID;
    int currentX;
    int currentY;
    boolean running = true;

    Counter(int X, int Y, int ID) {
        this.ID = ID;
        currentX = X;
        currentY = Y;

    }

    @Override
    public void run() {
        moveCard(currentX, currentY);
    }

    public void moveCard(int x, int y) {

        currentY = DisplayPanel.deckY;
        currentX = DisplayPanel.deckX;

        while (currentY != y) {
            currentY++;
           
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        while (currentX != x) {
            currentX--;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        
        running = false;

    }

}
