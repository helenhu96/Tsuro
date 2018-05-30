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
        Map<SPlayer, PlayerPosition> map = board.getPlayerToPosition();
        for (SPlayer player:map.keySet()) {
            PlayerPosition position = map.get(player);
            PawnLocation pawnLoc = new PawnLocation(position);
            String color = player.getColor();
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
