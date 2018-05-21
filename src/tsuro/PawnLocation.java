package tsuro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType (propOrder = {"h", "v", "a", "b"}, name = "pawn-loc")
public class PawnLocation {
    String h;
    String v;
    Integer a;
    Integer b;
    public PawnLocation() {}


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

    @XmlElement (name = "n")
    public void setA(Integer n) {
        this.a = n;
    }

    public Integer getA() {
        return this.a;
    }

    @XmlElement (name = "n")
    public void setB(Integer n) {
        this.b = n;
    }

    public Integer getB() {
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
