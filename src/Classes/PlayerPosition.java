package Classes;

public class PlayerPosition {
    private int y;
    private int x;
    private int spot;
    private boolean outOfBound;

    public PlayerPosition(int y, int x, int spot) {
        this.y = y;
        this.x = x;
        this.spot = spot;
        this.outOfBound = false;
    }

    public PlayerPosition(PlayerPosition p) {
        y = p.y;
        x = p.x;
        spot = p.spot;
        outOfBound = p.outOfBound;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getSpot() {
        return spot;
    }

    public boolean isOutOfBound() {
        return outOfBound;
    }

    public void setOutOfBound() {
        outOfBound = true;
    }

}
