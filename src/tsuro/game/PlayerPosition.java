package tsuro.game;

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

    public int getCorrespondingSpot(){
        switch (spot) {
            case 0: return 5;
            case 1: return 4;
            case 2: return 7;
            case 3: return 6;
            case 4: return 1;
            case 5: return 0;
            case 6: return 3;
            case 7: return 2;
            default: return -1;
        }
    }

    public boolean isOutOfBound() {
        return outOfBound;
    }

    public void setOutOfBound() {
        outOfBound = true;
    }

    public PlayerPosition getLeftPosition(){
        return new PlayerPosition(y, x-1, getCorrespondingSpot());
    }

    public PlayerPosition getRightPosition(){
        return new PlayerPosition(y, x+1, getCorrespondingSpot());
    }

    public PlayerPosition getAbovePosition(){
        return new PlayerPosition(y-1, x, getCorrespondingSpot());
    }

    public PlayerPosition getBelowPosition(){
        return new PlayerPosition(y+1, x, getCorrespondingSpot());
    }

    public PlayerPosition flip(){
        if ((x == 0 && (spot == 6 || spot == 7))
                || (x == 5 && (spot == 2 || spot == 3))
                || (y == 0 && (spot == 0 || spot == 1))
                || (y == 5 && (spot == 4 || spot == 5))){
            return this;
        }
        if (spot == 0 || spot == 1){
            return new PlayerPosition(y-1, x, getCorrespondingSpot());
        }
        if (spot == 2 || spot == 3){
            return new PlayerPosition(y, x+1, getCorrespondingSpot());
        }
        if (spot == 4 || spot == 5){
            return new PlayerPosition(y+1, x, getCorrespondingSpot());
        }
        else{
            return new PlayerPosition(y, x-1, getCorrespondingSpot());
        }
    }

    public boolean equals(PlayerPosition p) {
        return (this.getX() == p.getX() && this.getY() == p.getY() && this.getSpot() == p.getSpot());
    }

}