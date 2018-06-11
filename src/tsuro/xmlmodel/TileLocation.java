package tsuro.xmlmodel;

public class TileLocation {
    private Integer y;
    private Integer x;

    public TileLocation() {}
    public TileLocation(Integer y, Integer x) {
        this.y = y;
        this.x = x;
    }

    public void setX(Integer x) {
        this.x = x;
    }
    public Integer getX() {
        return this.x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
    public Integer getY() {
        return this.y;
    }

}
