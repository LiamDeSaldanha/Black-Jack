import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

public class Box {
    AtomicBoolean occupied;
    int Xpos;
    int Ypos;

    public Box(int Xpos, int Ypos) {
        this.Xpos = Xpos;
        this.Ypos = Ypos;
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.BLACK);
        g2.drawRect(Xpos - 5, Ypos - 5, Card.cardWidth + 10, Card.cardHeight + 10);

    }

    public boolean isOccupied() {
        return occupied.get();
    }

    public void setOccupied(boolean occupied) {
        this.occupied.set(occupied);
    }

    public int getXPos() {
        return Xpos;
    }

    public int getYPos() {
        return Ypos;
    }
}
