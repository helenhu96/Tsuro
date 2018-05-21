package tsuro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ent")
public class PawnEntry {
    String color;
    PawnLocation pawnLoc;

    public PawnEntry() {}

    public PawnEntry(String color, PawnLocation pawnLoc) {
        this.color = color;
        this.pawnLoc = pawnLoc;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    @XmlElement(name = "pawn-loc")
    public void setPawnLoc(PawnLocation loc) {
        this.pawnLoc = loc;
    }

    public PawnLocation getPawnLoc() {
        return pawnLoc;
    }

}
