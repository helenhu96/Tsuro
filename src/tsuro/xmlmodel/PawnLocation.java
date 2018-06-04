package tsuro.xmlmodel;

import tsuro.game.Board;
import tsuro.game.PlayerPosition;

import javax.xml.bind.annotation.*;

@XmlRootElement (name = "pawn-loc")
@XmlType (propOrder = {"h", "v", "a", "b"}, name = "pawn-loc")
public class PawnLocation {
    String h;
    String v;
    int a;
    int b;
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
                return new PlayerPosition(y, x, b % 2).flip();
            }
            if (a == 6) {
                y = 5;
                x = b / 2;
                if (b % 2 == 0) {
                    spot = 5;
                } else {
                    spot = 4;
                }
                return new PlayerPosition(y, x, spot).flip();
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
                    return testpp.flip();
                } else {
                    return testpp.getBelowPosition().flip();
                }
            }

        }else {
            if (a == 0){
                x = 0;
                y = b/2;
                spot = 7 - b % 2;
                return new PlayerPosition(y,x,spot);

            }
            if (a == 6){
                x = 5;
                y = b/2;
                spot = 2 + b % 2;
                return new PlayerPosition(y,x,spot);
            }
            else {
                x = a - 1;
                y = b / 2;
                spot = 2 + b % 2;

                PlayerPosition testpp = new PlayerPosition(y,x,spot);

                if (board.isTileOnBoard(testpp)){
                    return testpp.flip();
                } else {
                    return testpp.getRightPosition().flip();
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

    @XmlElement
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
