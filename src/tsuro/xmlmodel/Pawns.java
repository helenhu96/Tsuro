package tsuro.xmlmodel;
import tsuro.game.Board;
import tsuro.game.PlayerPosition;
import tsuro.game.SPlayer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class Pawns {
    List<PawnEntry> entries;
    public Pawns() {}
    public Pawns(Board board) {
        setPawnEntry(new ArrayList<>());
        Map<String, PlayerPosition> map = board.getColorToPosition();
        for (String color:map.keySet()) {
            PlayerPosition position = map.get(color);
            PawnLocation pawnLoc = new PawnLocation(position);
            PawnEntry entry = new PawnEntry(color, pawnLoc);
            addEntry(entry);
        }
    }


    @XmlElement(name = "ent")
    public void setPawnEntry(List<PawnEntry> entries) {
        this.entries = entries;
    }

    public List<PawnEntry> getPawnEntry(){
        return this.entries;
    }

    public void addEntry(PawnEntry entry) {
        this.entries.add(entry);
    }
}
