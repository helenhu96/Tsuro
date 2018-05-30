package tsuro.xmlmodel;

import tsuro.game.Board;
import tsuro.game.PlayerPosition;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
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

    public void writePawnMap(Map<String, PlayerPosition> map, Board b){
        map.put(this.color, pawnLoc.backtoPlayerPosition(b));
    }
}
