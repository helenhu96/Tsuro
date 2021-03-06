package tsuro.xmlmodel;
import tsuro.game.Board;
import tsuro.game.Tile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class Tiles {
    List<TileEntry> entries;

    public Tiles() {}

    public Tiles(Board board) {
        setTileEntry(new ArrayList<>());
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                Tile t = board.getTile(y, x);
                if (t != null) {
                    TileEntry entry = new TileEntry(new TileLocation(y, x), new ConvertedTile(t));
                    addEntry(entry);
                }
            }
        }
    }


    @XmlElement(name = "ent")
    public void setTileEntry(List<TileEntry> entries) {
        this.entries = entries;
    }

    public List<TileEntry> getTileEntry(){
        return this.entries;
    }

    public void addEntry(TileEntry entry) {
        this.entries.add(entry);
    }




}
