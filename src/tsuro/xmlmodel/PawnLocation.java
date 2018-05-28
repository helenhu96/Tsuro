package tsuro.xmlmodel;

import tsuro.game.Board;
import tsuro.game.PlayerPosition;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType (propOrder = {"h", "v", "a", "b"}, name = "pawn-loc")
public class PawnLocation {
    public String h;
    public String v;
    public int a;
    public int b;
    public PawnLocation() {}

    public PawnLocation(String h, String v, int a, int b){
        this.h = h;
        this.v = v;
        this.a = a;
        this.b = b;
    }

    public PawnLocation(PlayerPosition position) {
        int spot = position.getSpot();
        int y = position.getY();
        int x = position.getX();
        Number n = new Number();
        if (spot == 0 || spot == 1 ) {
            setA(y);
            setB(2 * x + spot);
            setH("");
        } else if (spot == 4 || spot ==5){
            setH("");
            setA(y + 1);
            setB(2 * x + 5 - spot);
        } else if (spot == 2 || spot ==3) {
            setV("");
            setA(x + 1);
            setB(2 * y + spot - 2);
        } else {
            setV("");
            setA(x);
            setB(2 * y + 7 - spot);
        }
    }


    // for the decoder
    public PlayerPosition backtoPlayerPosition(Board board){
        int y = 0;
        int x = 0;
        int spot = 0;
        if (h != null) {
            if (a == 0) {
                y = 0;
                x = b / 2;
                return new PlayerPosition(y, x, b % 2);
            } else if (a == 6) {
                y = 5;
                x = b / 2;
                if (b % 2 == 0) {
                    spot = 5;
                } else {
                    spot = 4;
                }
                return new PlayerPosition(y, x, spot);
            } else {
                y = a-1;
                x = b / 2;
                if (b % 2 == 0) {
                    spot = 5;
                } else {
                    spot = 4;
                }

                PlayerPosition testpp = new PlayerPosition(y, x, spot);

                if (board.isTileOnBoard(testpp)) {
                    return testpp;
                } else {
                    return testpp.getBelowPosition();
                }
            }

        }else {

            if (a == 0){
                x = 0;
                y = b/2;
                if (a%2 == 0){spot = 7;}
                else {spot = 6;}

                return new PlayerPosition(y,x,spot);

            } else if (a == 5){
                x = 5;
                y = b/2;
                if (a%2 == 0){spot = 2;}
                else {spot = 3;}
                return new PlayerPosition(y,x,spot);
            }
            else {
                x = a-1;
                y = b/2;
                if (b%2 == 0){
                    spot = 2;
                } else {spot = 3;}

                PlayerPosition testpp = new PlayerPosition(y,x,spot);

                if (board.isTileOnBoard(testpp)){
                    return testpp;
                } else {
                    return testpp.getRightPosition();
                }

            }

        }

    }



    @XmlElement (name = "n")
    public void setA(int n) {
        this.a = n;
    }

    public int getA() {
        return this.a;
    }

    @XmlElement (name = "n")
    public void setB(int n) {
        this.b = n;
    }

    public int getB() {
        return this.b;
    }

    @XmlElement ()
    public void setH(String h) {
        this.h = h;
    }

    public String getH() {
        return this.h;
    }

    @XmlElement
    public void setV(String v) {
        this.v = v;
    }
    public String getV() {
        return this.v;
    }


}
