package tsuro;

public class PlayerPosition {
    private int y;
    private int x;
    private int spot;
    private boolean outOfBound;


    public PlayerPosition(int y, int x, int spot) {
        if (y<0 || y>5 || x<0 || x >5 || spot<0 || spot>7) {
            throw new java.lang.IllegalArgumentException("Illegal input for PlayerPosition");
        }
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

    public void setSpot(int s) {
        if (spot<0 || spot>7)
            throw new java.lang.IllegalArgumentException("Illegal input");
        spot = s;
    }

    public boolean isOutOfBound() {
        return outOfBound;
    }

    public void setOutOfBound() {
        outOfBound = true;
    }


    public boolean equals(PlayerPosition p) {
        return (this.getX() == p.getX() && this.getY() == p.getY() && this.getSpot() == p.getSpot());
    }

}
